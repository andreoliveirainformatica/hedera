package com.hedera.hedera.usecase;


import com.hedera.hedera.entitiy.PaymentCard;

public interface PaymentManager {

    void payment(String orderNumber);

    String createToken(PaymentCard paymentCard);

    PaymentCard getPaymentCar(String tokenId);

    void delete(String tokenId);

}
