package com.test.productprices.application.mapper;

import com.test.productprices.application.dto.ProductPriceQueryDto;
import com.test.productprices.domain.ProductPrice;
import lombok.NoArgsConstructor;

/**
 * Mapper to construct ProductPriceQueryDto objects.
 */
@NoArgsConstructor
public class ProductPriceQueryMapper {

    /**
     * Maps ProductPrice object to ProductPriceQueryDto
     * @param productPrice the product price to map
     * @return the ProductPriceQueryDto obtained
     */
    public static ProductPriceQueryDto toProductPriceQueryDto(ProductPrice productPrice) {
        return ProductPriceQueryDto.builder()
                .price(productPrice.getPrice())
                .currency(productPrice.getCurrency())
                .productId(productPrice.getProductId())
                .brandId(productPrice.getBrandId())
                .startDate(productPrice.getStartDate())
                .endDate(productPrice.getEndDate())
                .feeId(productPrice.getFeeId())
                .build();
    }
}
