package com.test.productprices.application;

import com.test.productprices.application.dto.ProductPriceQueryDto;
import com.test.productprices.domain.exception.ProductPriceNotFoundException;
import com.test.productprices.application.repository.ProductPriceRepository;
import com.test.productprices.domain.ProductPrice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductPriceRetrieverTest {

    @InjectMocks
    private ProductPriceRetriever productPriceRetriever;

    @Mock
    private ProductPriceRepository productPriceRepository;

    @Test
    void test_getProductPrice_empty() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long brandId = 1L;
        Long productId = 35455L;

        Mockito.when(productPriceRepository.getProductPrice(date, productId, brandId))
                .thenReturn(Optional.empty());

        // When and Then
        Assertions.assertThrows(ProductPriceNotFoundException.class, () -> productPriceRetriever.getProductPrice(date, brandId, productId));
    }

    @Test
    void test_getProductPrice_allFieldsMapped_ok() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59);
        Long brandId = 1L;
        Long productId = 35455L;
        Long feeId = 2L;
        BigDecimal price = new BigDecimal("35.50");
        String currency = "EUR";

        ProductPrice productPrice = ProductPrice.builder()
                .brandId(brandId)
                .startDate(startDate)
                .endDate(endDate)
                .feeId(feeId)
                .productId(productId)
                .price(price)
                .currency(currency)
                .priority(1)
                .build();

        Mockito.when(productPriceRepository.getProductPrice(date, productId, brandId))
                .thenReturn(Optional.of(productPrice));

        // When
        ProductPriceQueryDto productPriceRetrieved =
                productPriceRetriever.getProductPrice(date, brandId, productId);

        // Then
        assertNotNull(productPriceRetrieved);
        assertEquals(productPrice.getBrandId(), productPriceRetrieved.getBrandId());
        assertEquals(productPrice.getStartDate(), productPriceRetrieved.getStartDate());
        assertEquals(productPrice.getEndDate(), productPriceRetrieved.getEndDate());
        assertEquals(productPrice.getFeeId(), productPriceRetrieved.getFeeId());
        assertEquals(productPrice.getProductId(), productPriceRetrieved.getProductId());
        assertEquals(productPrice.getPrice(), productPriceRetrieved.getPrice());
        assertEquals(productPrice.getCurrency(), productPriceRetrieved.getCurrency());
    }
  
}