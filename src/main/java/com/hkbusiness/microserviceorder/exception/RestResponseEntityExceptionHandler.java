package com.hkbusiness.microserviceorder.exception;

import com.hkbusiness.microserviceorder.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(OrderItemOutOfStockException.class)
    public ResponseEntity<ErrorMessage> orderItemOutOfStockException(OrderItemOutOfStockException exception, WebRequest request){
        ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorMessage> orderNotFoundException(OrderNotFoundException exception, WebRequest request){
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND,exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(ProductCodeNotFoundException.class)
    public ResponseEntity<ErrorMessage> productCodeNotFoundException(ProductCodeNotFoundException exception, WebRequest request){
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND,exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
