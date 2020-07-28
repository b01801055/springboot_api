package com.ron.springboot_api.exception;

import org.springframework.validation.Errors;

public class InvalidRequestException extends RuntimeException {
       private Errors errors;

    public InvalidRequestException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }
}
