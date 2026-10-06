package com.test.productprices.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProductPriceTest {

    @Test
    void should_BuildProductPrice_Correctly() {
        // Given
        Long expectedBrandId = 1L;
        LocalDateTime expectedStartDate = LocalDateTime.of(2026, 10, 6, 0, 0);
        LocalDateTime expectedEndDate = LocalDateTime.of(2026, 12, 31, 23, 59);
        Long expectedFeeId = 2L;
        Long expectedProductId = 35455L;
        int expectedPriority = 1;
        BigDecimal expectedPrice = new BigDecimal("25.50");
        String expectedCurrency = "EUR";

        // When
        ProductPrice productPrice = ProductPrice.builder()
                .id(1L)
                .brandId(expectedBrandId)
                .startDate(expectedStartDate)
                .endDate(expectedEndDate)
                .feeId(expectedFeeId)
                .productId(expectedProductId)
                .priority(expectedPriority)
                .price(expectedPrice)
                .currency(expectedCurrency)
                .build();

        // Then
        assertNotNull(productPrice);
        assertEquals(expectedBrandId, productPrice.getBrandId());
        assertEquals(expectedStartDate, productPrice.getStartDate());
        assertEquals(expectedEndDate, productPrice.getEndDate());
        assertEquals(expectedFeeId, productPrice.getFeeId());
        assertEquals(expectedProductId, productPrice.getProductId());
        assertEquals(expectedPriority, productPrice.getPriority());
        assertEquals(expectedPrice, productPrice.getPrice());
        assertEquals(expectedCurrency, productPrice.getCurrency());
    }
  
}