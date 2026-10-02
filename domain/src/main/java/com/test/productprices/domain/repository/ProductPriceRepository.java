package com.test.productprices.domain.repository;

import com.test.productprices.model.ProductPrice;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductPriceRepository {

    List<ProductPrice> getProductPrice(LocalDateTime datetime, Long productId, Long brandId);
}
