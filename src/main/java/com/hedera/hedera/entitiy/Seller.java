package com.hedera.hedera.entitiy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Seller {

    private String id;

    private String nome;

    private BigDecimal commissionPercent;

    private String accountId;

    private String contractId;
}