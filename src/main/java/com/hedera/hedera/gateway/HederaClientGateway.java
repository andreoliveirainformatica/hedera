package com.hedera.hedera.gateway;

import com.hedera.hashgraph.sdk.HederaException;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hashgraph.sdk.crypto.Key;

/**
 * Created by Grazeffe on 2019-08-08.
 * https://github.com/Grazeffe
 */

public interface HederaClientGateway {

    AccountId createAccount(final Key publicKey) throws HederaException;

    long getAccountBalance(final AccountId accountId) throws HederaException;

    void transferCredit(AccountId accountId, long amount) throws HederaException;

    void transferDebit(AccountId accountId, long amount) throws HederaException;
}