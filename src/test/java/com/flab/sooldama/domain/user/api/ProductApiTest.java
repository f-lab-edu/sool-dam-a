package com.flab.sooldama.domain.user.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @DisplayName("상품 조회 성공 테스트")
    public void getProducts() throws Exception {
        this.mockMvc.perform(get("/products")).andExpect(status().isOk());
    }
}
