package com.flab.sooldama.domain.product.api;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.flab.sooldama.domain.product.dto.response.ProductResponse;
import com.flab.sooldama.domain.product.exception.ProductNotFoundException;
import com.flab.sooldama.domain.product.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductApi.class)
public class ProductApiTest {

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	ProductApi productApi;

	@MockBean
	ProductService productService;

	private List<ProductResponse> products;

	@BeforeEach
	public void setUp() {
		ProductResponse product1 = ProductResponse.builder()
			.id(1L)
			.productCategoryId(1L)
			.name("백련 미스티 살균 막걸리")
			.price(4500)
			.imageUrl("https://www.sooldamhwa.com/images/common/mainLogo.png")
			.description("연꽃이 들어간 살균 막걸리")
			.abv(7.0)
			.capacity(375)
			.build();
		ProductResponse product2 = ProductResponse.builder()
			.id(2L)
			.productCategoryId(1L)
			.name("구름을 벗삼아")
			.price(20000)
			.imageUrl("https://www.sooldamhwa.com/images/common/mainLogo.png")
			.description("구름처럼 부드럽고 달콤한 막걸리")
			.abv(6.0)
			.capacity(500)
			.build();
		ProductResponse product3 = ProductResponse.builder()
			.id(3L)
			.productCategoryId(2L)
			.name("토박이 한산 소곡주")
			.price(11000)
			.imageUrl("https://www.sooldamhwa.com/images/common/mainLogo.png")
			.description("한 번 마시면 멈출 수 없는 맛")
			.abv(16.0)
			.capacity(500)
			.build();
		this.products = new ArrayList<ProductResponse>();
		products.add(product1);
		products.add(product2);
		products.add(product3);
	}

	@Test
	@DisplayName("제품 조회 성공 테스트")
	public void getProductsTest() throws Exception {
		this.mockMvc
			.perform(get("/products"))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("offset이 0 이하일 때 제품 조회 실패 테스트")
	public void getProductsFailTest() throws Exception {
		this.mockMvc
			.perform(get("/products?offset=-1"))
			.andExpect(status().isBadRequest());
	}

    @Test
    @DisplayName("아이디로 제품 조회 성공 테스트")
    public void getProductTest() throws Exception {
        this.mockMvc
			.perform(get("/products/1"))
			.andExpect(status().isOk());
    }
}
