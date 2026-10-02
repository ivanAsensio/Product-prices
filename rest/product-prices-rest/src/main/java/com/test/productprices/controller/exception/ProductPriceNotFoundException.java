package com.test.productprices.controller.exception;

/**
 * Exception dedicated to represent when a product price cannot be found.
 */
public class ProductPriceNotFoundException extends RuntimeException {

    public ProductPriceNotFoundException(String message) {
        super(message);
    }
}