package com.test.productprices.controller;

import com.test.productprices.api.PricesApi;
import com.test.productprices.application.ProductPriceRetriever;
import com.test.productprices.application.dto.ProductPriceQueryDto;
import com.test.productprices.controller.mapper.PriceMapper;
import com.test.productprices.rest.dto.PriceRetrieved;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
public class ProductPriceController implements PricesApi {

    private final ProductPriceRetriever productPriceRetriever;

    @Override
    public ResponseEntity<PriceRetrieved> retrieveProductPrice(
            @RequestParam(value = "datetime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datetime,
            @RequestParam(value = "productId") Long productId,
            @RequestParam(value = "brandId") Long brandId
    ) {
        ProductPriceQueryDto productPrice = productPriceRetriever.getProductPrice(datetime, productId, brandId);

        return ResponseEntity.ok(PriceMapper.toProductPriceDto(productPrice));
    }

}
