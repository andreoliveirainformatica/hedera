package com.hedera.hedera.gateway;

import com.hedera.hashgraph.sdk.HederaException;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hashgraph.sdk.crypto.Key;
import com.hedera.hashgraph.sdk.file.FileId;

/**
 * Created by Grazeffe on 2019-08-08.
 * https://github.com/Grazeffe
 */

public interface HederaClientGateway {

    AccountId createAccount(final Key publicKey) throws HederaException;

    long getAccountBalance(final AccountId accountId) throws HederaException;

    FileId createFile(String json) throws HederaException;

    String getFileContent(String fileIdParam) throws HederaException;

}