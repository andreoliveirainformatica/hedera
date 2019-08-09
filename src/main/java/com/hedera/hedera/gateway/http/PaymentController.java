package com.hedera.hedera.gateway.http;

import com.hedera.hedera.entitiy.PaymentCard;
import com.hedera.hedera.usecase.PaymentManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/api/payment/")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentManager paymentManager;

    @PostMapping(
            value = "token/",
            produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<String> createToken(@RequestBody PaymentCard paymentCard) {
        return ResponseEntity.ok(paymentManager.createToken(paymentCard));
    }

    @GetMapping(
            value = "token/{tokenId}",
            produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<PaymentCard> getPaymentCard2(@PathVariable final String tokenId) {
        return ResponseEntity.ok(paymentManager.getPaymentCar(tokenId));
    }

}
