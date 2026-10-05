package com.test.productprices.domain.exception;

/**
 * Exception dedicated to represent when a product price cannot be found.
 */
public class ProductPriceNotFoundException extends RuntimeException {

    public ProductPriceNotFoundException(String message) {
        super(message);
    }
}