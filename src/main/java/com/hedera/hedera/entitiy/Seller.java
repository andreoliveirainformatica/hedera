package com.hedera.hedera.entitiy;

import com.hedera.hashgraph.sdk.account.AccountId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Seller {

    private int id;

    private String nome;

    private BigDecimal commisisonPercent;

    private AccountId accountId;
}