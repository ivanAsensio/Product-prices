package com.test.productprices.domain.repository;

import com.test.productprices.model.ProductPrice;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for accessing product price data.
 */
public interface ProductPriceRepository {

    /**
     * Retrieves the product prices matching the specified criteria.
     *
     * @param datetime the date and time to look for
     * @param productId the product identifier
     * @param brandId the brand identifier
     * @return the list of product prices matching the specified criteria
     */
    List<ProductPrice> getProductPrice(LocalDateTime datetime, Long productId, Long brandId);
}
