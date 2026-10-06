package com.test.productprices.controller.handler;

import com.test.productprices.application.exception.InvalidProductPriceRequestException;
import com.test.productprices.domain.exception.ProductPriceNotFoundException;
import com.test.productprices.rest.dto.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void init() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void test_handleProductPriceNotFound() {
        // Given
        String errorMessage = "Price not found for given parameters";
        ProductPriceNotFoundException exception = new ProductPriceNotFoundException(errorMessage);

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleProductPriceNotFound(exception);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.NOT_FOUND.value(), body.getStatus());
        assertEquals("PRODUCT_PRICE_NOT_FOUND", body.getCode());
        assertEquals(errorMessage, body.getMessage());
    }

    @Test
    void test_handleInvalidProductPriceRequest() {
        // Given
        String errorMessage = "The date format is invalid";
        InvalidProductPriceRequestException exception = new InvalidProductPriceRequestException(errorMessage);

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleInvalidProductPriceRequest(exception);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.getStatus());
        assertEquals("INVALID_PRODUCT_PRICE_REQUEST", body.getCode());
        assertEquals(errorMessage, body.getMessage());
    }

    @Test
    void test_handleException() {
        // Given & When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleException(new RuntimeException());

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        ErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.getStatus());
        assertEquals("INTERNAL_SERVER_ERROR", body.getCode());
        assertEquals("An unexpected error occurred", body.getMessage());
    }

    @Test
    void test_handleMissingServletRequestParameter_badRequest() {

        MissingServletRequestParameterException exception =
                new MissingServletRequestParameterException(
                        "productId",
                        "Long"
                );

        ResponseEntity<ErrorResponse> response =
                globalExceptionHandler.handleBadRequest(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
        assertEquals(
                "INVALID_PRODUCT_PRICE_REQUEST",
                response.getBody().getCode()
        );
    }

    @Test
    void test_handleMethodArgumentTypeMismatch_badRequest() {

        MethodArgumentTypeMismatchException exception =
                new MethodArgumentTypeMismatchException(
                        "invalid",
                        Long.class,
                        "productId",
                        null,
                        null
                );

        ResponseEntity<ErrorResponse> response =
                globalExceptionHandler.handleBadRequest(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
        assertEquals(
                "INVALID_PRODUCT_PRICE_REQUEST",
                response.getBody().getCode()
        );
    }

    @Test
    void test_handleConstraintViolationException_badRequest() {
        // Given
        ConstraintViolationException exception = Mockito.mock(ConstraintViolationException.class);
        Mockito.when(exception.getMessage()).thenReturn("productId must be greater than 0");

        // When
        ResponseEntity<ErrorResponse> response =
                globalExceptionHandler.handleBadRequest(exception);

        // Then

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("INVALID_PRODUCT_PRICE_REQUEST", response.getBody().getCode());
        assertEquals(
                "productId must be greater than 0",
                response.getBody().getMessage()
        );
    }

}