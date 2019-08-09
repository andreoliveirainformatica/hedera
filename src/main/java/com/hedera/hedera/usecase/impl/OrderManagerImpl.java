package com.hedera.hedera.usecase.impl;

import com.hedera.hedera.entitiy.Order;
import com.hedera.hedera.gateway.OrderGateway;
import com.hedera.hedera.usecase.OrderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderManagerImpl implements OrderManager {

    private final OrderGateway orderGateway;

    @Override
    public void create(Order order) {
        orderGateway.create(order);
    }

    @Override
    public Order findById(String orderNumber) {
        return orderGateway.findById(orderNumber);
    }

}
