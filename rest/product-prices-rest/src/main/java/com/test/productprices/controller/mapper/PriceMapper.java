package com.test.productprices.controller.mapper;

import com.test.productprices.application.dto.ProductPriceQueryDto;
import com.test.productprices.domain.PriceRetrieved;
import lombok.NoArgsConstructor;

/**
 * Mapper to create PriceRetrieved objects.
 */
@NoArgsConstructor
public class PriceMapper {

    /**
     * Create a new PriceRetrieved using a ProductPriceQueryDto as reference.
     * @param productPrice the product price
     * @return the new product price retrieved.
     */
    public static PriceRetrieved toProductPriceDto(ProductPriceQueryDto productPrice) {
        PriceRetrieved response = new PriceRetrieved();
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
