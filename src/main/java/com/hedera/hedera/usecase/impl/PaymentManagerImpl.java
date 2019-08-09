package com.hedera.hedera.usecase.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hedera.hashgraph.sdk.HederaException;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hashgraph.sdk.proto.AccountID;
import com.hedera.hedera.config.helper.TinybarsCalculatorHelper;
import com.hedera.hedera.entitiy.Order;
import com.hedera.hedera.entitiy.PaymentCard;
import com.hedera.hedera.entitiy.Product;
import com.hedera.hedera.entitiy.Seller;
import com.hedera.hedera.gateway.HederaClientGateway;
import com.hedera.hedera.gateway.SellerGateway;
import com.hedera.hedera.usecase.PaymentManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PaymentManagerImpl implements PaymentManager {

    private final ObjectMapper objectMapper;
    private final HederaClientGateway hederaClientGateway;
    private final SellerGateway sellerGateway;
    private final TinybarsCalculatorHelper calculatorHelper;

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
    public void payment(Order order) {

        final List<PaymentCard> payments = order
                .getPayments()
                .stream()
                .map(payment -> getPaymentCar(payment.getToken()))
                .collect(Collectors.toList());


        final Map<Seller, BigDecimal> sellerBigDecimalMap = splitPayment(order);

        sellerBigDecimalMap.forEach(this::transferCredit);
    }

    private void transferCredit(Seller seller, BigDecimal value) {
        try {
            hederaClientGateway.transferCredit(
                AccountId.fromString(seller.getAccountId()),
                calculatorHelper.toTinybars(value.longValue() * 100));
        } catch (HederaException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
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
        try {
            hederaClientGateway.deleteFile(tokenId);
        } catch (HederaException  e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
