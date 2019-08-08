package com.hedera.hedera.usecase;

import com.hedera.hedera.entitiy.Seller;

public interface SellerManager {

   void createSelller(final Seller seller);

   long getBalancer(final Seller seller);

}
