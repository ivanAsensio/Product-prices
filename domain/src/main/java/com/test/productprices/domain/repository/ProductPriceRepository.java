package com.test.productprices.domain.repository;

import com.test.productprices.domain.ProductPrice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for accessing product price data.
 */
public interface ProductPriceRepository {

    /**
     * Retrieves the product price matching the specified criteria.
     *
     * @param datetime the date and time to look for
     * @param productId the product identifier
     * @param brandId the brand identifier
     * @return the list of product prices matching the specified criteria
     */
    Optional<ProductPrice> getProductPrice(LocalDateTime datetime, Long productId, Long brandId);
}
