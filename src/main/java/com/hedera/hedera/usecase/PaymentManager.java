package com.hedera.hedera.usecase;


import com.hedera.hedera.entitiy.Order;
import com.hedera.hedera.entitiy.PaymentCard;

public interface PaymentManager {

    void payment(Order order);

    String createToken(PaymentCard paymentCard);

    PaymentCard getPaymentCar(String tokenId);

}
