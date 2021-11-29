package com.lucasconte.tenpochallenge.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/operation")
public class OperationController {

    @GetMapping("/sum")
    public ResponseEntity<BigDecimal> sum(@RequestParam @NotNull BigDecimal number0,
                                            @RequestParam @NotNull BigDecimal number1) {
        return ResponseEntity.ok(number0.add(number1));
    }
}
