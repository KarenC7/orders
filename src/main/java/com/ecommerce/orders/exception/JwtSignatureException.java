package com.ecommerce.orders.exception;

public class JwtSignatureException extends RuntimeException {
    public JwtSignatureException(String message, Throwable cause) {
        super(message, cause);
    }
}