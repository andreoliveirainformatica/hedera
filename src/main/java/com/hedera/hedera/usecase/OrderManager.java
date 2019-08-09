package com.hedera.hedera.usecase;


import com.hedera.hedera.entitiy.Order;

public interface OrderManager {

    void create(Order order);

    Order findById(String orderNumber);

}
