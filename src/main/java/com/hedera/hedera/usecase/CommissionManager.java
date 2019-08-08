package com.hedera.hedera.usecase;

import com.hedera.hedera.entitiy.Seller;

import java.math.BigDecimal;

public interface CommissionManager {

    void credit(Seller seller, BigDecimal value);

    void deduct(Seller seller, BigDecimal value);


}
