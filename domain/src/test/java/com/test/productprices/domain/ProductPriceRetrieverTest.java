package com.test.productprices.domain;

import com.test.productprices.domain.exception.InvalidProductPriceRequestException;
import com.test.productprices.domain.repository.ProductPriceRepository;
import com.test.productprices.model.ProductPrice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductPriceRetrieverTest {

    @InjectMocks
    private ProductPriceRetriever productPriceRetriever;

    @Mock
    private ProductPriceRepository productPriceRepository;

    @Test
    void test_getProductPrice_nullDate_ko() {
        // Given
        LocalDateTime date = null;
        Long brandId = 1L;
        Long productId = 35455L;

        // When & Then
        assertThrows(
                InvalidProductPriceRequestException.class,
                () -> productPriceRetriever.getProductPrice(date, brandId, productId)
        );
    }

    @Test
    void test_getProductPrice_emptyList_ok() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long brandId = 1L;
        Long productId = 35455L;

        Mockito.when(productPriceRepository.getProductPrice(date, productId, brandId))
                .thenReturn(List.of());

        // When
        Optional<ProductPrice> productPrice =
                productPriceRetriever.getProductPrice(date, brandId, productId);

        // Then
        assertTrue(productPrice.isEmpty());
    }

    @Test
    void test_getProductPrice_singlePrice_ok() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long brandId = 1L;
        Long productId = 35455L;

        ProductPrice productPrice = ProductPrice.builder().price(BigDecimal.ONE).priority(1).build();

        Mockito.when(productPriceRepository.getProductPrice(date, productId, brandId))
                .thenReturn(List.of(productPrice));

        // When
        Optional<ProductPrice> productPriceRetrieved =
                productPriceRetriever.getProductPrice(date, brandId, productId);

        // Then
        assertTrue(productPriceRetrieved.isPresent());
        assertEquals(productPrice, productPriceRetrieved.get());
    }

    @Test
    void test_getProductPrice_highestPriority_ok() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long brandId = 1L;
        Long productId = 35455L;

        ProductPrice lowPriorityPrice = ProductPrice.builder().price(BigDecimal.ZERO).priority(1).build();

        ProductPrice highPriorityPrice = ProductPrice.builder().price(BigDecimal.ONE).priority(2).build();

        Mockito.when(productPriceRepository.getProductPrice(date, productId, brandId))
                .thenReturn(List.of(lowPriorityPrice, highPriorityPrice));

        // When
        Optional<ProductPrice> productPriceRetrieved =
                productPriceRetriever.getProductPrice(date, brandId, productId);

        // Then
        assertTrue(productPriceRetrieved.isPresent());
        assertEquals(highPriorityPrice, productPriceRetrieved.get());
    }
  
}