package com.hedera.hedera.usecase.impl;

import com.hedera.hashgraph.sdk.HederaException;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hedera.entitiy.Seller;
import com.hedera.hedera.gateway.HederaClientGateway;
import com.hedera.hedera.usecase.CommissionManager;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommissionManagerImpl implements CommissionManager {

  private final HederaClientGateway hederaClientGateway;

  @Override
  public void credit(Seller seller, BigDecimal value) {
    try {
      hederaClientGateway.transferCredit(AccountId.fromString(seller.getAccountId()), value.longValue());
    } catch (HederaException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void debit(Seller seller, BigDecimal value) {
    try {
      hederaClientGateway.transferDebit(AccountId.fromString(seller.getAccountId()), value.longValue());
    } catch (HederaException e) {
      e.printStackTrace();
    }
  }
}
