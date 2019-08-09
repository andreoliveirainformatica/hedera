package com.hedera.hedera.gateway.http;

import com.hedera.hedera.entitiy.Order;
import com.hedera.hedera.entitiy.PaymentCard;
import com.hedera.hedera.usecase.PaymentManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/api/order/")
@RequiredArgsConstructor
public class OrderController {

    private final PaymentManager paymentManager;

    @PostMapping(
            produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<String> createOrder(@RequestBody Order order) {

//        final String token = paymentManager.createToken(paymentCard);
//
//        return ResponseEntity
//                .created(ServletUriComponentsBuilder
//                        .fromCurrentRequest()
//                        .queryParam("token={token}")
//                        .buildAndExpand(token)
//                        .toUri())
//                .contentType(MediaType.TEXT_PLAIN)
//                .body(token);

        return null;
    }

    @GetMapping(
            value = "token/{tokenId}",
            produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<PaymentCard> getPaymentCard2(@PathVariable final String tokenId) {
        return ResponseEntity.ok(paymentManager.getPaymentCar(tokenId));
    }

}
