package com.hedera.hedera.gateway;

import com.hedera.hashgraph.sdk.HederaException;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hashgraph.sdk.contract.ContractId;
import com.hedera.hashgraph.sdk.crypto.Key;
import com.hedera.hashgraph.sdk.file.FileId;

import java.math.BigDecimal;

public interface HederaClientGateway {

    AccountId createAccount(final Key publicKey) throws HederaException;

    long getAccountBalance(final AccountId accountId) throws HederaException;

    void transferCredit(AccountId accountId, long amount) throws HederaException;

    void transferDebit(AccountId accountId, long amount) throws HederaException;

    FileId createFile(String json) throws HederaException;

    String getFileContent(String fileIdParam) throws HederaException;

    void deleteFile(String fileIdParam);

    ContractId createSmartContract(BigDecimal commission);

    String getSmartContract(String contractIdParam);
}