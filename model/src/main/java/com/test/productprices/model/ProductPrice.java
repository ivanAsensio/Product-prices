package com.test.productprices.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The product price aggregate.
 */
@Builder
@Getter
public class ProductPrice {

    private Long brandId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long priceList;

    private Long productId;

    private int priority;

    private BigDecimal price;

    private String currency;
}
