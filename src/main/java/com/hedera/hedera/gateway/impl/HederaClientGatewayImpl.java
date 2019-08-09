package com.hedera.hedera.gateway.impl;

import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.HederaException;
import com.hedera.hashgraph.sdk.TransactionId;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hashgraph.sdk.account.CryptoTransferTransaction;
import com.hedera.hashgraph.sdk.crypto.Key;
import com.hedera.hashgraph.sdk.crypto.ed25519.Ed25519PrivateKey;
import com.hedera.hashgraph.sdk.file.FileContentsQuery;
import com.hedera.hashgraph.sdk.file.FileCreateTransaction;
import com.hedera.hashgraph.sdk.file.FileDeleteTransaction;
import com.hedera.hashgraph.sdk.file.FileId;
import com.hedera.hedera.gateway.HederaClientGateway;
import java.time.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HederaClientGatewayImpl implements HederaClientGateway {

    private final Client hederaClient;

    @Value("${hedera.operator.id}")
    private String operatorId;

    @Value("${hedera.operator.key}")
    private String operatorKey;

    @Override
    public AccountId createAccount(Key publicKey) throws HederaException {
        hederaClient.setMaxTransactionFee(100_000_000L);
        return hederaClient.createAccount(publicKey, 0L);
    }

    @Override
    public long getAccountBalance(AccountId accountId) throws HederaException {
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

    @Override
    public FileId createFile(String json) throws HederaException {
        // Content to be stored in the file
        var fileContents = json.getBytes();
        hederaClient.setMaxTransactionFee(100_000_000L);

        // Create the new file and set its properties
        var newFile = new FileCreateTransaction(hederaClient)
                .addKey(Ed25519PrivateKey.fromString(operatorKey).getPublicKey()) // The public key of the owner of the file
                .setContents(fileContents) // Contents of the file
                .setExpirationTime(Instant.now().plus(Duration.ofSeconds(2592000))) // Set file expiration time in seconds
                .executeForReceipt(); // Submits transaction to the network and returns receipt which contains file ID

        return newFile.getFileId();
    }

    @Override
    public String getFileContent(String fileIdParam) throws HederaException {

        FileId fileId = FileId.fromString(fileIdParam);
        var contents = new FileContentsQuery(hederaClient)
                .setFileId(fileId)
                .execute();

        return contents.getFileContents().getContents().toStringUtf8();
    }

    @Override
    public void deleteFile(String fileIdParam) throws HederaException {

        FileId fileId = FileId.fromString(fileIdParam);
        new FileDeleteTransaction(hederaClient)
                .setFileId(fileId)
                .executeForReceipt();

    }
}