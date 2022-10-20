package com.flab.sooldama.domain.product.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductApiTest {

    @Autowired MockMvc mockMvc;

    @Test
    @DisplayName("제품 조회 성공 테스트")
    public void getProductsTest() throws Exception {
        this.mockMvc
                .perform(get("/products"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("offset이 0 이하일 때 제품 조회 실패")
    public void getProductsFailTest() throws Exception {
        this.mockMvc
                .perform(get("/products?offset=-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("categoryId를 사용하여 categoryId에 알맞는 제품 조회 성공")
    public void getProductsByCategoryIdTest() throws Exception {
        this.mockMvc
                .perform(get("/products?categoryId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productCategoryId").value(1));
    }
}
