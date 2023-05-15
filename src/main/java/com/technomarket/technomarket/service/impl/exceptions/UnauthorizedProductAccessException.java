package com.technomarket.technomarket.service.impl.exceptions;

public class UnauthorizedProductAccessException extends RuntimeException{

    public UnauthorizedProductAccessException(String message) {
        super(message);
    }

    public UnauthorizedProductAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
