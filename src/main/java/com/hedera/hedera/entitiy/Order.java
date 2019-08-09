package com.hedera.hedera.entitiy;

import lombok.Data;

import java.util.List;

@Data
public class Order {

    private String orderNumber;

    private List<Product> items;

    private List<Payment> payments;


}
