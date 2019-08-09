package com.hedera.hedera.gateway.impl;

import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.HederaException;
import com.hedera.hashgraph.sdk.Transaction;
import com.hedera.hashgraph.sdk.TransactionId;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hashgraph.sdk.account.CryptoTransferTransaction;
import com.hedera.hashgraph.sdk.crypto.Key;
import com.hedera.hedera.gateway.HederaClientGateway;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HederaClientGatewayImpl implements HederaClientGateway {

    private final Client hederaClient;

    @Value("${hedera.operator.id}")
    private String operatorId;

    @Override
    public AccountId createAccount(Key publicKey) throws HederaException {
        hederaClient.setMaxTransactionFee(100_000_000L);
        return hederaClient.createAccount(publicKey, 0L);
    }

    @Override
    public long getAccountBalance(AccountId accountId) throws HederaException {
        hederaClient.setMaxTransactionFee(100_000_000L);
        return hederaClient.getAccountBalance(accountId);
    }

    @Override
    public void transferCredit(AccountId accountId, long amount) throws HederaException {
        hederaClient.setMaxTransactionFee(100_000_000L);
        TransactionId transactionId = new CryptoTransferTransaction(hederaClient)
            .addSender(AccountId.fromString(operatorId), amount)
            .addRecipient(accountId, amount)
            .execute();
    }

    @Override
    public void transferDebit(AccountId accountId, long amount) throws HederaException {
        hederaClient.setMaxTransactionFee(100_000_000L);
        TransactionId transactionId = new CryptoTransferTransaction(hederaClient)
            .addSender(accountId, amount)
            .addRecipient(AccountId.fromString(operatorId), amount)
            .execute();
    }
}