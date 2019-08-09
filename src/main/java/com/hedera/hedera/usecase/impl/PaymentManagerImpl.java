package com.hedera.hedera.usecase.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hedera.hashgraph.sdk.HederaException;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hedera.config.helper.TinybarsCalculatorHelper;
import com.hedera.hedera.entitiy.*;
import com.hedera.hedera.gateway.HederaClientGateway;
import com.hedera.hedera.gateway.SellerGateway;
import com.hedera.hedera.usecase.OrderManager;
import com.hedera.hedera.usecase.PaymentManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PaymentManagerImpl implements PaymentManager {

    private final ObjectMapper objectMapper;
    private final HederaClientGateway hederaClientGateway;
    private final SellerGateway sellerGateway;
    private final TinybarsCalculatorHelper calculatorHelper;
    private final OrderManager orderManager;

    @Override
    public String createToken(PaymentCard paymentCard) {

        try {
            final String jsonCard = objectMapper.writeValueAsString(paymentCard);
            return hederaClientGateway.createFile(jsonCard).toString();
        } catch (JsonProcessingException | HederaException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public PaymentCard getPaymentCar(String tokenId) {

        try {
            final String fileContent = hederaClientGateway.getFileContent(tokenId);

            return objectMapper.readValue(fileContent, PaymentCard.class);
        } catch (HederaException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void payment(String orderNumber) {

        final Order order = orderManager.findById(orderNumber);


        final Map<Seller, BigDecimal> sellerBigDecimalMap = splitPayment(order);

        sellerBigDecimalMap.forEach(this::transferCredit);

        new ArrayList<>(order
                .getPayments())
                .forEach(p -> {
                    hederaClientGateway.deleteFile(p.getToken());
                });
    }

    private void transferCredit(Seller seller, BigDecimal value) {

        validateCommission(seller);




        try {
            final BigDecimal valueToTransfer = value.multiply((BigDecimal.valueOf(100).subtract(seller.getCommissionPercent())).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN));
            hederaClientGateway.transferCredit(
                AccountId.fromString(seller.getAccountId()),
                calculatorHelper.toTinybars(valueToTransfer.longValue() * 100));
        } catch (HederaException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void validateCommission(Seller seller) {
        final String smartContract = hederaClientGateway.getSmartContract(seller.getContractId());
        if (!seller.getCommissionPercent().equals(new BigDecimal(smartContract))) {
            throw new RuntimeException("Invalid Seller Commission! Seller Commission: " + seller.getCommissionPercent().toString() + " Contract Commission " + smartContract );
        }
    }

    private Map<Seller, BigDecimal> splitPayment(Order order) {

        Map<Seller, BigDecimal> map = new HashMap<>();
        order
                .getItems()
                .stream()
                .map(Product::getSellerId)
                .collect(Collectors.toSet())
                .forEach(s -> {

                    final BigDecimal valueOfSeller = BigDecimal.valueOf(order
                            .getItems()
                            .stream()
                            .filter(product -> s.equals(product.getSellerId()))
                            .mapToDouble(p -> p.getQuantity().multiply(p.getUnitPrice()).doubleValue())
                            .sum());

                    map.put(sellerGateway.findById(s).orElse(null), valueOfSeller);

                });

        return map;


    }

    @Override
    public void delete(String tokenId) {

            hederaClientGateway.deleteFile(tokenId);
    }

}
