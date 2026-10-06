package com.test.productprices.controller;

import com.test.productprices.application.ProductPriceRetriever;
import com.test.productprices.application.dto.ProductPriceQueryDto;
import com.test.productprices.domain.PriceRetrieved;
import com.test.productprices.domain.ProductPrice;
import com.test.productprices.domain.exception.ProductPriceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductPriceControllerTest {

    @InjectMocks
    private ProductPriceController productPriceController;

    @Mock
    private ProductPriceRetriever productPriceRetriever;

    @Test
    void test_retrieveProductPrice() {
        // Given
        LocalDateTime datetime = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        ProductPriceQueryDto productPrice = ProductPriceQueryDto.builder()
                .productId(productId)
                .brandId(brandId)
                .price(new BigDecimal("25.45"))
                .currency("EUR")
                .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .feeId(2L).build();

        Mockito.when(productPriceRetriever.getProductPrice(datetime, productId, brandId))
                .thenReturn(productPrice);

        // When
        ResponseEntity<PriceRetrieved> response =
                productPriceController.retrieveProductPrice(datetime, productId, brandId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        PriceRetrieved result = response.getBody();

        assertEquals(productId, result.getProductId());
        assertEquals(brandId, result.getBrandId());
        assertEquals(new BigDecimal("25.45"), result.getPrice());
        assertEquals("EUR", result.getCurrency());
        assertEquals(LocalDateTime.of(2020, 6, 14, 15, 0), result.getStartDate());
        assertEquals(LocalDateTime.of(2020, 6, 14, 18, 30), result.getEndDate());
        assertEquals(2L, result.getFeeId());

        Mockito.verify(productPriceRetriever).getProductPrice(datetime, productId, brandId);
    }

    @Test
    void shouldPropagateExceptionWhenProductPriceCannotBeRetrieved() {
        // Given
        LocalDateTime datetime = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Mockito.when(productPriceRetriever.getProductPrice(datetime, productId, brandId))
                .thenThrow(new ProductPriceNotFoundException("Product price is not found"));

        // When / Then
        assertThrows(
                ProductPriceNotFoundException.class,
                () -> productPriceController.retrieveProductPrice(
                        datetime,
                        productId,
                        brandId
                )
        );

        Mockito.verify(productPriceRetriever).getProductPrice(datetime, productId, brandId);
    }

}