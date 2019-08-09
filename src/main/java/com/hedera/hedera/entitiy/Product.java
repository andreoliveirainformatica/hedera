package com.hedera.hedera.entitiy;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {

    private String sku;

    private BigDecimal unitPrice;

    private BigDecimal quantity;

    private String sellerId;

}
