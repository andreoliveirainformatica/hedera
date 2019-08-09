package com.hedera.hedera.gateway.http;

import com.hedera.hedera.config.helper.TinybarsCalculatorHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/api/convert/")
@RequiredArgsConstructor
public class ConvertController {

    private final TinybarsCalculatorHelper tinybarsCalculatorHelper;

    @GetMapping(
            value = "toreal/{tiny}",
            produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<BigDecimal> toreal(@PathVariable final long tiny) {
        return ResponseEntity.ok(BigDecimal.valueOf(tinybarsCalculatorHelper.toRealInCents(tiny)).divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_EVEN));
    }

    @GetMapping(
            value = "totiny/{dollar}",
            produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<Long> totiny(@PathVariable final BigDecimal dollar) {
        return ResponseEntity.ok(tinybarsCalculatorHelper.toTinybars(dollar.multiply(BigDecimal.valueOf(100)).longValue()));
    }

}
