package com.hedera.hedera.gateway.impl;

import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.HederaException;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hashgraph.sdk.crypto.Key;
import com.hedera.hedera.gateway.HederaClientGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HederaClientGatewayImpl implements HederaClientGateway {

    private final Client hederaClient;

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

//        //Print the file ID to console
//        System.out.println("The new file ID is " + newFile.getFileId().toString());
//
//        // Get file contents
//        var contents = new FileContentsQuery(hederaClient)
//                .setFileId(newFile.getFileId())
//                .execute();
//
//        // Prints query results to console
//        System.out.println("File content query results: " + contents.getFileContents().getContents().toStringUtf8());
    }

    public String getFileContent(String fileIdParam) throws HederaException {

        FileId fileId = FileId.fromString(fileIdParam);
        var contents = new FileContentsQuery(hederaClient)
                .setFileId(fileId)
                .execute();

        return contents.getFileContents().getContents().toStringUtf8();
    }
}