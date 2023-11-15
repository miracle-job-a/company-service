package com.miracle.companyservice.exception;

public class UnauthorizedTokenException extends RuntimeException{
    public UnauthorizedTokenException(String message) {
        super(message);
    }
}
