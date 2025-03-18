package com.ecommerce.orders.controller.exception;

import com.ecommerce.orders.exception.InvalidOrderException;
import com.ecommerce.orders.exception.JwtSignatureException;
import com.ecommerce.orders.exception.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JwtSignatureException.class)
    public ResponseEntity<String> handleJwtSignatureException(JwtSignatureException ex) {
        return new ResponseEntity<>("Invalid JWT signature: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException ex) {
        return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<String> handleInvalidOrderException(InvalidOrderException ex) {
        return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}