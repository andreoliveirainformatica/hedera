package com.hedera.hedera.usecase;

import com.hedera.hedera.entitiy.Seller;

public interface SellerManager {

    Seller createSelller(final Seller seller);

    long getBalance(final String sellerId);

    Seller getSelller(final String sellerId);

    String getContractInfo(String sellerId);
}