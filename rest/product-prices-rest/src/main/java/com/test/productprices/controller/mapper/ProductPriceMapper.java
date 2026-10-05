package com.test.productprices.controller.mapper;

import com.test.productprices.domain.ProductPrice;
import com.test.productprices.domain.ProductPriceRetrieved;
import lombok.NoArgsConstructor;

/**
 * Mapper to create ProductPriceRetrieved objects.
 */
@NoArgsConstructor
public class ProductPriceMapper {

    /**
     * Create a new ProductPriceRetrieved using a ProductPrice as reference.
     * @param productPrice the product price
     * @return the new product price retrieved.
     */
    public static ProductPriceRetrieved toProductPriceDto(ProductPrice productPrice) {
        ProductPriceRetrieved response = new ProductPriceRetrieved();
        response.setPrice(productPrice.getPrice());
        response.setCurrency(productPrice.getCurrency());
        response.setProductId(productPrice.getProductId());
        response.setBrandId(productPrice.getBrandId());
        response.setStartDate(productPrice.getStartDate());
        response.setEndDate(productPrice.getEndDate());
        response.setFeeId(productPrice.getFeeId());
        return response;
    }
}
