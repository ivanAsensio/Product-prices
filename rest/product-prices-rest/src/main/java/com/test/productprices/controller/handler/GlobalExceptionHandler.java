package com.test.productprices.controller.handler;

import com.test.productprices.domain.exception.ProductPriceNotFoundException;
import com.test.productprices.application.exception.InvalidProductPriceRequestException;
import com.test.productprices.rest.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.logging.Logger;

/**
 * The exception handler for ProductPrice REST API.
 */
@RestControllerAdvice(basePackages = "com.test.productprices.controller")
public class GlobalExceptionHandler {

    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    private static final String PRODUCT_PRICE_NOT_FOUND_CODE =
            "PRODUCT_PRICE_NOT_FOUND";

    private static final String INVALID_PRODUCT_PRICE_REQUEST_CODE =
            "INVALID_PRODUCT_PRICE_REQUEST";

    private static final String INTERNAL_SERVER_ERROR_CODE =
            "INTERNAL_SERVER_ERROR";

    @ExceptionHandler(ProductPriceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductPriceNotFound(
            ProductPriceNotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        PRODUCT_PRICE_NOT_FOUND_CODE,
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(InvalidProductPriceRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidProductPriceRequest(
            InvalidProductPriceRequestException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        INVALID_PRODUCT_PRICE_REQUEST_CODE,
                        exception.getMessage()
                ));
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception exception) {

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(
                        400,
                        INVALID_PRODUCT_PRICE_REQUEST_CODE,
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {

        logger.severe(String.format("An error occurred with description: [%s]", e.getMessage()));

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        INTERNAL_SERVER_ERROR_CODE,
                        "An unexpected error occurred"
                ));
    }
}