package com.example.CircuitBreakerPattern.service;

import com.example.CircuitBreakerPattern.dto.CircuitBreakerDto;
import com.example.CircuitBreakerPattern.mapper.CircuitBreakerMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CircuitBreakerService {

    private final CircuitBreakerMapper circuitBreakerMapper;

    @Value("${resilience4j.circuitbreaker.configs.default.sliding-window-size}")
    private int slidingWindowSize;

    @Value("${resilience4j.circuitbreaker.configs.default.failure-rate-threshold}")
    private int failureRateThreshold;

    @Value("${resilience4j.circuitbreaker.configs.default.wait-duration-in-open-state}")
    private String waitDurationInOpenState;

    @Value("${resilience4j.circuitbreaker.configs.default.permitted-number-of-calls-in-half-open-state}")
    private int permittedNumberOfCallsInHalfOpenState;

    @Value("${resilience4j.circuitbreaker.configs.default.sliding-window-type}")
    private String slidingWindowType;

    public CircuitBreakerService(CircuitBreakerMapper circuitBreakerMapper) {
        this.circuitBreakerMapper = circuitBreakerMapper;
    }

    @CircuitBreaker(name = "circuitBreakerService", fallbackMethod = "fallbackForGetCircuitBreaker")
    public CircuitBreakerDto getCircuitBreaker(String id) {
        if (id.equals("may9noy") || id.equals("김승현") || id.equals("zzanggu")) {
            throw new RuntimeException("Service failure " + id);
        }
        return circuitBreakerMapper.findUserById(id);
    }

    public CircuitBreakerDto fallbackForGetCircuitBreaker(String id, Throwable t) {
        String logMessage = "Error occurred " + id;

        log.error(logMessage);
        insertLogMessage(logMessage);

        log.error(":::::::: Error occurred ::::::::");
        log.error("Error occurred while fetching user with id: {}", id);
        log.error("서킷 브레이커 작동됨...: {}", id, t);

        log.info("Current Circuit Breaker Configuration:");
        log.info("Sliding Window Size: {}", slidingWindowSize);
        log.info("Failure Rate Threshold: {}", failureRateThreshold);
        log.info("Wait Duration in Open State: {}", waitDurationInOpenState);
        log.info("Permitted Number of Calls in Half Open State: {}", permittedNumberOfCallsInHalfOpenState);
        log.info("Sliding Window Type: {}", slidingWindowType);

        return new CircuitBreakerDto();
    }

    public void insertLogMessage(String insertLogMessage) {
        circuitBreakerMapper.insertLogMessage(insertLogMessage);
    }

    public void fallbackForCreateCircuitBreaker(CircuitBreakerDto user, Throwable t) {
        log.error("Error occurred while creating user with id: {}", user.getId(), t);
    }
}
