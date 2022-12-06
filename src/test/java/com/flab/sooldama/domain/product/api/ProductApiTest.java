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

@WebMvcTest(controllers = ProductApi.class)
public class ProductApiTest {

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	ProductApi productApi;

	@MockBean
	ProductService productService;

	private List<ProductResponse> products;

	private static final Integer DEFAULT_OFFSET = 0;

	private static final Integer DEFAULT_LIMIT = 20;

	private static final Long DEFAULT_CATEGORY_ID = null;

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
	@DisplayName("한 번에 여러 제품 조회 시 기본값 적용")
	public void getProductsTest() throws Exception {
		// 테스트 데이터 및 동작 정의
		when(productService.getProducts(DEFAULT_OFFSET, DEFAULT_LIMIT, DEFAULT_CATEGORY_ID))
			.thenReturn(this.products);

		// 실행
		this.mockMvc
			.perform(get("/products")
				.param("offset", DEFAULT_OFFSET.toString())
				.param("limit", DEFAULT_LIMIT.toString()))
			.andExpect(status().isOk());

		// 행위 검증
		verify(productService, times(1))
			.getProducts(DEFAULT_OFFSET, DEFAULT_LIMIT, DEFAULT_CATEGORY_ID);
	}

	@Test
	@DisplayName("offset이 0보다 작으면 유효성 검증 실패해서 service로 요청 전달 X")
	public void getProductsFailTest() throws Exception {
		Integer INVALID_OFFSET = -1;

		this.mockMvc
			.perform(get("/products")
				.param("offset", INVALID_OFFSET.toString())
				.param("limit", DEFAULT_LIMIT.toString()))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("아이디로 제품 조회 성공 테스트")
	public void getProductTest() throws Exception {
		// 테스트 데이터 및 동작 정의
		Long PRODUCT_ID = 1L;

		when(productService.getProductById(PRODUCT_ID)).thenReturn(this.products.get(0));

		// 실행
		this.mockMvc
			.perform(get("/products/{PRODUCT_ID}", PRODUCT_ID))
			.andDo(print())
			.andExpect(status().isOk());

		// 행위 검증
		verify(productService, times(1)).getProductById(PRODUCT_ID);
	}

	@Test
	@DisplayName("존재하지 않는 제품 아이디로 제품 조회 시 실패")
	public void getProductByNonExistingId() throws Exception {
		// 테스트 데이터 및 동작 정의
		Long NONEXISTING_ID = -1L;

		doThrow(ProductNotFoundException.class).when(productService)
			.getProductById(NONEXISTING_ID);

		// 실행
		this.mockMvc
			.perform(get("/products/{NONEXISTING_ID}", NONEXISTING_ID))
			.andDo(print())
			.andExpect(status().isNotFound());

		// 행위 검증
		verify(productService, times(1)).getProductById(NONEXISTING_ID);
	}
}
