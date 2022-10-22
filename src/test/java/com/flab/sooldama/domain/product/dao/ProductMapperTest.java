package com.flab.sooldama.domain.product.dao;

import static org.junit.jupiter.api.Assertions.*;

import com.flab.sooldama.domain.product.domain.Product;
import com.flab.sooldama.domain.product.exception.ProductNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductMapperTest {

	@Autowired
	private ProductMapper productMapper;

	@Test
	@DisplayName("전체 제품 조회 테스트")
	public void selectProductsTest() {

		List<Product> products = productMapper.selectProducts(0, 1, null);

		assertEquals(1L, products.get(0).getProductCategoryId());
		assertEquals("백련 미스티 살균 막걸리", products.get(0).getName());
		assertEquals(4500, products.get(0).getPrice());
		assertEquals("연꽃이 들어간 살균 막걸리", products.get(0).getDescription());
		assertEquals(7.0, products.get(0).getAbv());
		assertEquals(375, products.get(0).getCapacity());
	}

	@Test
	@DisplayName("카테고리별 제품 조회 테스트")
	public void selectProductsByCategoryIdTest() {
		List<Product> products = productMapper.selectProducts(0, 1, 1L);

		for (Product product : products) {
			assertEquals(1L, product.getProductCategoryId());
		}
	}

    @Test
    @DisplayName("아이디로 제품 조회 테스트")
    public void selectProductByIdTest() {
        Optional<Product> product = productMapper.selectProductById(1L);

        if (product.isPresent()) {
            assertEquals(1L, product.get().getId());
        } else {
            throw new ProductNotFoundException("제품이 존재하지 않습니다.");
        }
    }
}
