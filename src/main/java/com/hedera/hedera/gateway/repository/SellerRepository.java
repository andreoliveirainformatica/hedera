package com.hedera.hedera.gateway.repository;

import com.hedera.hedera.entitiy.Seller;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends MongoRepository<Seller, String> {
}
