package com.test.productprices.domain;

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

    private Long id;

    private Long brandId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long feeId;

    private Long productId;

    private int priority;

    private BigDecimal price;

    private String currency;
}
