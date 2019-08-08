package com.hedera.hedera.entitiy;

import com.hedera.hashgraph.sdk.account.AccountId;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Seller {

    private int id;

    private String nome;

    private BigDecimal commisisonPercent;

    private AccountId accountId;

}
