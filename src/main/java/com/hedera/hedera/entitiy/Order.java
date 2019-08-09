package com.hedera.hedera.entitiy;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Order {

    @Id
    private String orderNumber;

    private List<Product> items;

    private List<Payment> payments;


}
