package com.vendingMachine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserExceptions extends RuntimeException {
    public UserExceptions(String entityName, String identifier) {
        super(String.format("%s with identifier %s caused an error", entityName, identifier));
    }
}
