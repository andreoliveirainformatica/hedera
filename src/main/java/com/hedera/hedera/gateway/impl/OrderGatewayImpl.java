package com.hedera.hedera.gateway.impl;

import com.hedera.hedera.entitiy.Order;
import com.hedera.hedera.gateway.OrderGateway;
import com.hedera.hedera.gateway.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderGatewayImpl implements OrderGateway {

    private final OrderRepository orderRepository;

    @Override
    public void create(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Order findById(String orderNumber) {
        return orderRepository.findById(orderNumber).orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
