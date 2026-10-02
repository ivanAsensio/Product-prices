package com.test.productprices.infrastructure.repository;

import com.test.productprices.model.ProductPrice;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductPriceExtractor implements ResultSetExtractor<List<ProductPrice>> {

    @Override
    public List<ProductPrice> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<ProductPrice> productPriceList = new ArrayList<>();
        while (rs.next()) {
            ProductPrice productPrice = ProductPrice.builder()
                    .productId(rs.getLong("PRODUCT_ID"))
                    .brandId(rs.getLong("BRAND_ID"))
                    .price(rs.getBigDecimal("PRICE"))
                    .priceList(rs.getLong("PRICE_LIST"))
                    .priority(rs.getInt("PRIORITY"))
                    .currency(rs.getString("CURRENCY"))
                    .startDate(rs.getObject("START_DATE", LocalDateTime.class))
                    .endDate(rs.getObject("END_DATE", LocalDateTime.class))
                    .build();
            productPriceList.add(productPrice);
        }


        return productPriceList;
    }
}
