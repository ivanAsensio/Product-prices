package com.test.productprices.domain.exception;

/**
 * Exception thrown when the product price request contains invalid data.
 */
public class InvalidProductPriceRequestException extends RuntimeException {

    public InvalidProductPriceRequestException(String message) {
        super(message);
    }
}