package com.hedera.hedera.gateway.http;

import com.hedera.hedera.entitiy.PaymentCard;
import com.hedera.hedera.usecase.PaymentManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

        final String token = paymentManager.createToken(paymentCard);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .buildAndExpand(token)
                        .toUri())
                .contentType(MediaType.TEXT_PLAIN)
                .body(token);
    }

    @GetMapping(
            value = "token/{tokenId}",
            produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<PaymentCard> getPaymentCard2(@PathVariable final String tokenId) {
        return ResponseEntity.ok(paymentManager.getPaymentCar(tokenId));
    }

    @DeleteMapping(
            value = "token/{tokenId}",
            produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> deletePaymentCard2(@PathVariable final String tokenId) {
         paymentManager.delete(tokenId);
         return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
