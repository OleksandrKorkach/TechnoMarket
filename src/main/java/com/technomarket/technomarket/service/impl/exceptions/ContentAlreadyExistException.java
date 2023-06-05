package com.technomarket.technomarket.service.impl.exceptions;

public class ContentAlreadyExistException extends RuntimeException{

    public ContentAlreadyExistException(String message) {
        super(message);
    }

    public ContentAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
