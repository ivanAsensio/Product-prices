package com.test.productprices.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
public class ProductPriceQueryDto {

    private Long brandId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long feeId;

    private Long productId;

    private BigDecimal price;

    private String currency;
}
