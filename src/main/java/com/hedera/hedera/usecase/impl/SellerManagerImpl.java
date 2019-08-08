package com.hedera.hedera.usecase.impl;

import com.hedera.hashgraph.sdk.HederaException;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hashgraph.sdk.crypto.ed25519.Ed25519PrivateKey;
import com.hedera.hedera.entitiy.Seller;
import com.hedera.hedera.gateway.HederaClientGateway;
import com.hedera.hedera.usecase.SellerManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Created by Grazeffe on 2019-08-08.
 * https://github.com/Grazeffe
 */
@Component
@RequiredArgsConstructor
public class SellerManagerImpl implements SellerManager {

    private final HederaClientGateway hederaClientGateway;

    @Override
    public Seller createSelller(Seller seller) {
        final long initialBalance = 0L;
        try {
            // TODO: 2019-08-08 buscar KEY
            Ed25519PrivateKey newKey = Ed25519PrivateKey.generate();
            AccountId accountId = hederaClientGateway.createAccount(newKey.getPublicKey(), initialBalance);
            seller.setAccountId(accountId);

        } catch (HederaException e) {
            e.printStackTrace();
        }
        return seller;
    }

    @Override
    public long getBalance(Seller seller) {
        long accountBalance = 0L;
        try {
            accountBalance = hederaClientGateway.getAccountBalance(seller.getAccountId());
        } catch (HederaException e) {
            e.printStackTrace();
        }
        return accountBalance;
    }
}