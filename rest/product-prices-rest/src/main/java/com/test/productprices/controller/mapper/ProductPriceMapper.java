package com.test.productprices.controller.mapper;

import com.test.productprices.model.ProductPrice;
import com.test.productprices.model.ProductPriceRetrieved;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductPriceMapper {

    public static ProductPriceRetrieved toProductPriceDto(ProductPrice productPrice) {
        ProductPriceRetrieved response = new ProductPriceRetrieved();
        response.setPrice(productPrice.getPrice());
        response.setProductId(productPrice.getProductId());
        response.setBrandId(productPrice.getBrandId());
        response.setStartDate(productPrice.getStartDate());
        response.setEndDate(productPrice.getEndDate());

        return response;
    }
}
