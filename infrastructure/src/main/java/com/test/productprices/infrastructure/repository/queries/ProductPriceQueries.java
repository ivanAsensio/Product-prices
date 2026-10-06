package com.test.productprices.infrastructure.repository.queries;

public class ProductPriceQueries {

    public static final String GET_PRODUCT_PRICE_BY_DATE_PRODUCT_ID_BRAND_ID =
        """
                SELECT PRODUCT_PRICE_ID, BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, PRIORITY, PRICE, CURRENCY
                FROM PRODUCT_PRICES
                WHERE START_DATE <= ?
                  AND END_DATE >= ?
                  AND PRODUCT_ID = ?
                  AND BRAND_ID = ?
                ORDER BY PRIORITY DESC
                LIMIT 1;""";
}
