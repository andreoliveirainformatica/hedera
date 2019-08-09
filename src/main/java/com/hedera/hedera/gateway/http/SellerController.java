package com.hedera.hedera.gateway.http;

import com.hedera.hedera.entitiy.Seller;
import com.hedera.hedera.usecase.SellerManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(
            value = "{sellerId}",
            produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<Seller> getSeller(@PathVariable String sellerId) {
        return ResponseEntity.ok(sellerManager.getSelller(sellerId));
    }

    @GetMapping(
            value = "balance/{sellerId}",
            produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<Long> getAccountSeller(@PathVariable String sellerId) {
        return ResponseEntity.ok(sellerManager.getBalance(sellerId));
    }

}
