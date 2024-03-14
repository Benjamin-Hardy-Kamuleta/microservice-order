package com.hkbusiness.microserviceorder.exception;

public class OrderItemOutOfStockException extends Exception{
    public OrderItemOutOfStockException() {
    }

    public OrderItemOutOfStockException(String message) {
        super(message);
    }

    public OrderItemOutOfStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderItemOutOfStockException(Throwable cause) {
        super(cause);
    }
}
