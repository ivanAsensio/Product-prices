package com.test.productprices;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProductpricesApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_retrieveProductPrices_Day14_1000() throws Exception {
        mockMvc.perform(get("/product-prices?datetime=2020-06-14T10:00:00&productId=35455&brandId=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(JSON_EXPECTED_T1));
    }

    @Test
    void test_retrieveProductPrices_Day14_1600() throws Exception {
        mockMvc.perform(get("/product-prices?datetime=2020-06-14T16:00:00&productId=35455&brandId=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(JSON_EXPECTED_T2));
    }

    @Test
    void test_retrieveProductPrices_Day14_2100() throws Exception {
        mockMvc.perform(get("/product-prices?datetime=2020-06-14T21:00:00&productId=35455&brandId=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(JSON_EXPECTED_T1));
    }

    @Test
    void test_retrieveProductPrices_Day15_1000() throws Exception {
        mockMvc.perform(get("/product-prices?datetime=2020-06-15T10:00:00&productId=35455&brandId=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(JSON_EXPECTED_T3));
    }

    @Test
    void test_retrieveProductPrices_Day16_2100() throws Exception {
        mockMvc.perform(get("/product-prices?datetime=2020-06-16T21:00:00&productId=35455&brandId=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(JSON_EXPECTED_T4));
    }


    private static final String JSON_EXPECTED_T1 = """
        {"productId":35455,"brandId":1,"price":35.50,"startDate":"2020-06-14T00:00:00","endDate":"2020-12-31T23:59:59","currency":"EUR","feeId":1}
    """;

    private static final String JSON_EXPECTED_T2 = """
        {"productId":35455,"brandId":1,"price":25.45,"startDate":"2020-06-14T15:00:00","endDate":"2020-06-14T18:30:00","currency":"EUR","feeId":2}
    """;

    private static final String JSON_EXPECTED_T3 = """
        {"productId":35455,"brandId":1,"price":30.50,"startDate":"2020-06-15T00:00:00","endDate":"2020-06-15T11:00:00","currency":"EUR","feeId":3}
    """;

    private static final String JSON_EXPECTED_T4 = """
       {"productId":35455,"brandId":1,"price":38.95,"startDate":"2020-06-15T16:00:00","endDate":"2020-12-31T23:59:59","currency":"EUR","feeId":4}
    """;
  
}