package com.hedera.hedera.gateway.http;

import com.hedera.hedera.entitiy.Order;
import com.hedera.hedera.usecase.OrderManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/api/order/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderManager orderManager;

    @PostMapping(
            produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {

        orderManager.create(order);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .queryParam("orderNumber={orderNumber}")
                        .buildAndExpand(order)
                        .toUri())
                .contentType(MediaType.APPLICATION_JSON)
                .body(order);
    }

    @GetMapping(
            value = "{orderNumber}",
            produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<Order> getOrder(@PathVariable final String orderNumber) {
        return ResponseEntity.ok(orderManager.findById(orderNumber));
    }

}
