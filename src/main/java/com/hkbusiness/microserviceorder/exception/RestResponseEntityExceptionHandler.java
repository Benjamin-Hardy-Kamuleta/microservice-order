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
        ErrorMessage message = new ErrorMessage(HttpStatus.PARTIAL_CONTENT,exception.getMessage());
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(message);
    }
}
