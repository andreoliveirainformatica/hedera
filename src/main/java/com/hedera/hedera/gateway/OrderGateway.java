package com.hedera.hedera.gateway;

import com.hedera.hedera.entitiy.Order;

public interface OrderGateway {

  void create(Order order);

  Order findById(String orderNumber);
}
