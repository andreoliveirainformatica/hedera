package com.hedera.hedera.gateway.repository;

import com.hedera.hedera.entitiy.Seller;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SellerRepository extends MongoRepository<Seller, Integer> {

}
