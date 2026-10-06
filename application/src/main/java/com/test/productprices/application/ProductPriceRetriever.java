package com.test.productprices.application;

import com.test.productprices.application.dto.ProductPriceQueryDto;
import com.test.productprices.application.mapper.ProductPriceQueryMapper;
import com.test.productprices.domain.exception.ProductPriceNotFoundException;
import com.test.productprices.application.repository.ProductPriceRepository;
import com.test.productprices.domain.ProductPrice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Retriever to obtain product prices.
 */
@AllArgsConstructor
@Component
public class ProductPriceRetriever {

    private final ProductPriceRepository productPriceRepository;

    /**
     * Get product price by date, brandId and productId
     * @param date the date
     * @param brandId the brand id
     * @param productId the product id
     * @return the product price retrieved from the system. Could be empty in case of not exist
     */
    public ProductPriceQueryDto getProductPrice(LocalDateTime date, Long brandId, Long productId) {
        ProductPrice productPrice = productPriceRepository.getProductPrice(date, productId, brandId)
                .orElseThrow(() -> new ProductPriceNotFoundException("There is no product price retrieved"));

        return ProductPriceQueryMapper.toProductPriceQueryDto(productPrice);
    }
}
