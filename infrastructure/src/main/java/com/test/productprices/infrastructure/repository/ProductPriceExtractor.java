package com.test.productprices.infrastructure.repository;

import com.test.productprices.domain.ProductPrice;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Extractor to obtain product price data.
 */
@Component
public class ProductPriceExtractor implements ResultSetExtractor<Optional<ProductPrice>> {

    @Override
    public Optional<ProductPrice> extractData(ResultSet rs) throws SQLException, DataAccessException {
        if (!rs.next()) {
            return Optional.empty();
        }
        return Optional.of(ProductPrice.builder()
                    .id(rs.getLong("PRODUCT_PRICE_ID"))
                    .productId(rs.getLong("PRODUCT_ID"))
                    .brandId(rs.getLong("BRAND_ID"))
                    .price(rs.getBigDecimal("PRICE"))
                    .feeId(rs.getLong("PRICE_LIST"))
                    .priority(rs.getInt("PRIORITY"))
                    .currency(rs.getString("CURRENCY"))
                    .startDate(rs.getObject("START_DATE", LocalDateTime.class))
                    .endDate(rs.getObject("END_DATE", LocalDateTime.class))
                    .build());
    }
}
