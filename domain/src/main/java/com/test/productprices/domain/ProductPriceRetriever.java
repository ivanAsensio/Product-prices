package com.test.productprices.domain;

import com.test.productprices.domain.exception.InvalidProductPriceRequestException;
import com.test.productprices.domain.repository.ProductPriceRepository;
import com.test.productprices.model.ProductPrice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public Optional<ProductPrice> getProductPrice(LocalDateTime date, Long brandId, Long productId) {
        if (date == null || brandId == null || productId == null) {
            throw new InvalidProductPriceRequestException("Date, brandId and productId cannot be null");
        }

        List<ProductPrice> productPriceList = productPriceRepository.getProductPrice(date, productId, brandId);

        if (CollectionUtils.isEmpty(productPriceList)) {
            return Optional.empty();
        }

        if (productPriceList.size() == 1) {
            return Optional.of(productPriceList.getFirst());
        }

        return getProductPriceByPriority(productPriceList);
    }

    private Optional<ProductPrice> getProductPriceByPriority(List<ProductPrice> productPriceList) {
        ProductPrice productPriceObtained = null;
        for (ProductPrice productPrice : productPriceList) {
            if (productPriceObtained == null || productPrice.getPriority() > productPriceObtained.getPriority()) {
                productPriceObtained = productPrice;
            }
        }
        if (productPriceObtained != null) {
            return Optional.of(productPriceObtained);
        }
        return Optional.empty();
    }
}
