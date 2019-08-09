package com.hedera.hedera.usecase.impl;

import com.hedera.hashgraph.sdk.HederaException;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hashgraph.sdk.crypto.ed25519.Ed25519PrivateKey;
import com.hedera.hedera.entitiy.Seller;
import com.hedera.hedera.gateway.HederaClientGateway;
import com.hedera.hedera.gateway.SellerGateway;
import com.hedera.hedera.usecase.SellerManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by Grazeffe on 2019-08-08.
 * https://github.com/Grazeffe
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SellerManagerImpl implements SellerManager {

    private final HederaClientGateway hederaClientGateway;
    private final SellerGateway sellerGateway;

    @Override
    public Seller createSelller(Seller seller) {
        try {
            Ed25519PrivateKey newKey = Ed25519PrivateKey.generate();
            AccountId accountId = hederaClientGateway.createAccount(newKey.getPublicKey());
            seller.setAccountId(accountId.toString());
            sellerGateway.save(seller);
        } catch (HederaException ex) {
            log.error(ex.getMessage());
        }
        return seller;
    }

    @Override
    public long getBalance(String sellerId) {
        long accountBalance = 0L;
        try {
            Seller seller = sellerGateway.findById(sellerId).get();
            accountBalance = hederaClientGateway.getAccountBalance(AccountId.fromString(seller.getAccountId()));
        } catch (HederaException ex) {
            log.error(ex.getMessage());
        }
        return accountBalance;
    }

    @Override
    public Seller getSelller(String sellerId) {
        return sellerGateway.findById(sellerId).orElseThrow(() -> new RuntimeException("Seller Not Found"));
    }
}