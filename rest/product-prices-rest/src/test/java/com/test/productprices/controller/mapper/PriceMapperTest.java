package com.test.productprices.controller.mapper;

import com.test.productprices.application.dto.ProductPriceQueryDto;
import com.test.productprices.domain.PriceRetrieved;
import com.test.productprices.domain.ProductPrice;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PriceMapperTest {

    @Test
    void test_toProductPriceDto_ok() {
        // Given
        ProductPriceQueryDto productPrice = ProductPriceQueryDto.builder()
                .productId(35455L)
                .brandId(1L)
                .price(new BigDecimal("25.45"))
                .currency("EUR")
                .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .feeId(2L)
                .build();

        // When
        PriceRetrieved result = PriceMapper.toProductPriceDto(productPrice);

        // Then
        assertEquals(productPrice.getProductId(), result.getProductId());
        assertEquals(productPrice.getBrandId(), result.getBrandId());
        assertEquals(productPrice.getPrice(), result.getPrice());
        assertEquals(productPrice.getCurrency(), result.getCurrency());
        assertEquals(productPrice.getStartDate(), result.getStartDate());
        assertEquals(productPrice.getEndDate(), result.getEndDate());
        assertEquals(productPrice.getFeeId(), result.getFeeId());
    }
}