package com.test.productprices.infrastructure.repository;

import com.test.productprices.domain.repository.ProductPriceRepository;
import com.test.productprices.infrastructure.repository.queries.ProductPriceQueries;
import com.test.productprices.domain.ProductPrice;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * H2 implementation to obtain the product price data.
 */
@AllArgsConstructor
@Component
public class H2ProductPriceRepository implements ProductPriceRepository {

    private final JdbcTemplate jdbcTemplate;

    private final ProductPriceExtractor productPriceExtractor;

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductPrice> getProductPrice(LocalDateTime datetime, Long productId, Long brandId) {
        return jdbcTemplate.query(buildGetPreparedStatement(datetime, productId, brandId), productPriceExtractor);
    }

    private PreparedStatementCreator buildGetPreparedStatement(LocalDateTime datetime, Long productId, Long brandId) {
        return con -> {
            final PreparedStatement ps = con.prepareStatement(ProductPriceQueries.GET_PRODUCT_PRICE_BY_DATE_PRODUCT_ID_BRAND_ID);

            java.sql.Timestamp sqlTimestamp = (datetime != null) ? java.sql.Timestamp.valueOf(datetime) : null;

            ps.setTimestamp(1, sqlTimestamp);
            ps.setTimestamp(2, sqlTimestamp);
            ps.setLong(3, brandId);
            ps.setLong(4, productId);

            return ps;
        };
    }
}
