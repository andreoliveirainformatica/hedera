package com.hedera.hedera.gateway.http;

import com.hedera.hedera.entitiy.Seller;
import com.hedera.hedera.usecase.SellerManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/api/seller/")
@RequiredArgsConstructor
public class SellerController {

    private final SellerManager sellerManager;

    @PostMapping(
            produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) {
        return ResponseEntity.ok(sellerManager.createSelller(seller));
    }

//    @GetMapping(
//            produces = {APPLICATION_JSON_VALUE})
//    public ResponseEntity<Long> getAccountSeller(Seller seller) {
//        return ResponseEntity.ok(sellerManager.createSelller(seller));
//    }

}
