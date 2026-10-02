package com.test.productprices.controller;

import com.test.productprices.controller.mapper.ProductPriceMapper;
import com.test.productprices.domain.ProductPriceRetriever;
import com.test.productprices.model.ProductPrice;
import com.test.productprices.model.ProductPriceRetrieved;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class ProductPriceController implements com.test.productprices.api.ProductPricesApi {

    private final ProductPriceRetriever productPriceRetriever;

    @Override
    public ResponseEntity<ProductPriceRetrieved> retrieveProductPrice(
            @RequestParam(value = "datetime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datetime,
            @RequestParam(value = "productId") Long productId,
            @RequestParam(value = "brandId") Long brandId
    ) {
        Optional<ProductPrice> productPrice = productPriceRetriever.getProductPrice(datetime, productId, brandId);
        if (productPrice.isEmpty()) {
            throw new RuntimeException("There is no product price retrieved");
        }

        return new ResponseEntity<>(ProductPriceMapper.toProductPriceDto(productPrice.get()), HttpStatus.OK);
    }

}
