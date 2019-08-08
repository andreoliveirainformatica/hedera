package com.hedera.hedera.usecase;


import com.hedera.hedera.entitiy.Order;

public interface PaymentManager {

    void payment(Order order);

}
