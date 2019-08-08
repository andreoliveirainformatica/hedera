package com.hedera.hedera.gateway;

import com.hedera.hedera.entitiy.Seller;
import java.util.Optional;

public interface SellerGateway {

  Optional<Seller> findById(String id);

  void save(Seller seller);
}
