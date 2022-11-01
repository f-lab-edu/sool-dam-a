package com.flab.sooldama.domain.product.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.flab.sooldama.domain.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductApi.class)
public class ProductApiTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	ProductService productService;

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
}
