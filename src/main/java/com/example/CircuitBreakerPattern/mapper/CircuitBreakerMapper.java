package com.example.CircuitBreakerPattern.mapper;

import com.example.CircuitBreakerPattern.dto.CircuitBreakerDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CircuitBreakerMapper {

    CircuitBreakerDto findUserById(String id);

    default String insertUser(CircuitBreakerDto user) {
        return null;
    }

    void insertLogMessage(String LogMessage);

}

