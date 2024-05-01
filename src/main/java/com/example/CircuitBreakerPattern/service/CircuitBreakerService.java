package com.example.CircuitBreakerPattern.service;

import com.example.CircuitBreakerPattern.dto.CircuitBreakerDto;
import com.example.CircuitBreakerPattern.mapper.CircuitBreakerMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CircuitBreakerService {

    private final CircuitBreakerMapper circuitBreakerMapper;

    public CircuitBreakerService(CircuitBreakerMapper circuitBreakerMapper) {
        this.circuitBreakerMapper = circuitBreakerMapper;
    }

    @CircuitBreaker(name = "circuitBreakerService", fallbackMethod = "fallbackForGetCircuitBreaker")
    public CircuitBreakerDto getCircuitBreaker(String id) {

        // if String id = "may9noy" then throw exception
        if (id.equals("may9noy")) {
            throw new RuntimeException("Service failure" + id);
        }

        // else if String id = "may9noy" then throw exception
        if (id.equals("김승현")) {
            throw new RuntimeException("Service failure" + id);
        }

        // els if String id = "김승현" then throw exception
        if (id.equals("zzanggu")) {
            throw new RuntimeException("Service failure" + id);
        }

        return circuitBreakerMapper.findUserById(id);

    }

    @CircuitBreaker(name = "circuitBreakerService", fallbackMethod = "fallbackForCreateCircuitBreaker")
    public void createCircuitBreaker(CircuitBreakerDto user) {
        circuitBreakerMapper.insertUser(user);
    }

    public void insertLogMessage(String insertLogMessage) {
        circuitBreakerMapper.insertLogMessage(insertLogMessage);
    }

    public CircuitBreakerDto fallbackForGetCircuitBreaker(String id, Throwable t) {

        String LogMessage = "Error occurred " + id;

        // insert LogMessage to log file
        log.error(LogMessage);
        // insert log message to postgresql
        insertLogMessage(LogMessage);
        // error message output console
        System.out.println(":::::::: Error occurred ::::::::");
        // output error message
        System.out.println("Error occurred while fetching user with id: " + id);
        // log error message
        log.error("Error occurred while fetching user with id: " + id);
        // log error stack trace
        // log.error("Error occurred while fetching user with id: " + id, t);

        return new CircuitBreakerDto();

    }

    public void fallbackForCreateCircuitBreaker(CircuitBreakerDto user, Throwable t) {

        // output error message
        System.out.println("Error occurred while creating user with id: " + user.getId());
        // log error message
        log.error("Error occurred while creating user with id: " + user.getId());
        // log error stack trace
        log.error("Error occurred while creating user with id: " + user.getId(), t);

    }

}
