package com.hedera.hedera.gateway.impl;

import com.hedera.hedera.entitiy.Seller;
import com.hedera.hedera.gateway.SellerGateway;
import com.hedera.hedera.gateway.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SellerGatewayImpl implements SellerGateway {

    private final SellerRepository sellerRepository;

    public Optional<Seller> findById(String id) {
        return sellerRepository.findById(id);
    }

    public void save(Seller seller) {
        sellerRepository.save(seller);
    }
}
