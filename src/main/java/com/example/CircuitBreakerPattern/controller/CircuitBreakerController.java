package com.example.CircuitBreakerPattern.controller;

import com.example.CircuitBreakerPattern.dto.CircuitBreakerDto;
import com.example.CircuitBreakerPattern.service.CircuitBreakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CircuitBreakerController {

    @Autowired
    CircuitBreakerService circuitBreakerService;

    @GetMapping("/circuit-breaker")
    public CircuitBreakerDto circuitBreaker(String id) {
        return circuitBreakerService.getCircuitBreaker(id);
    }

}
