package com.vendingMachine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityName, String identifier) {
        super(String.format(": %s not found with identifier: %s", entityName, identifier));
    }
} 