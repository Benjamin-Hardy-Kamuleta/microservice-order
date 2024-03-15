package com.hkbusiness.microserviceorder.exception;

public class ProductCodeNotFoundException extends Exception{
    public ProductCodeNotFoundException() {
    }

    public ProductCodeNotFoundException(String message) {
        super(message);
    }

    public ProductCodeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductCodeNotFoundException(Throwable cause) {
        super(cause);
    }
}
