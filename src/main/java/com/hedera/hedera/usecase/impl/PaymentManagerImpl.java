package com.hedera.hedera.usecase.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hedera.hashgraph.sdk.HederaException;
import com.hedera.hedera.entitiy.Order;
import com.hedera.hedera.entitiy.PaymentCard;
import com.hedera.hedera.gateway.HederaClientGateway;
import com.hedera.hedera.usecase.PaymentManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class PaymentManagerImpl implements PaymentManager {

    private final ObjectMapper objectMapper;

    private final HederaClientGateway hederaClientGateway;

    @Override
    public void payment(Order order) {

    }

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
}
