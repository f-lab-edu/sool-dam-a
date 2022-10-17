package com.flab.sooldama.domain.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.flab.sooldama.domain.product.dao.ProductMapper;
import com.flab.sooldama.domain.product.domain.Product;
import com.flab.sooldama.domain.product.dto.response.ProductsResponse;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductServiceTest {

    /*
    @InjectMocks, @Mock 어노테이션
    @Mock 으로 mock 객체를 생성하여, @InjectMocks 가 붙은 객체에 주입시킵니다.
    @InjectMocks(Service) @Mock(DAO) 를 활용하여 Service 테스트 목객체에 DAO 목객체를 주입시킵니다.
     */
    @InjectMocks ProductService productService;
    @Mock private ProductMapper productMapper;

    @Test
    @DisplayName("제품이 존재할 때 조회 성공 테스트 - offeet, limit에 알맞는 리스트를 반환")
    public void getProductsTest() {

        // given
        int offset = 0;
        int limit = 5;

        List<Product> products = new ArrayList<>();
        for (int i = offset; i < limit; i++) {
            products.add(
                    Product.builder()
                            .productCategoryId(1L)
                            .name("test")
                            .price(1000)
                            .imageUrl("test")
                            .description("test")
                            .abv(1.0)
                            .capacity(350)
                            .build());
        }
        when(productMapper.selectProducts(offset, limit)).thenReturn(products);

        // when
        ProductsResponse productsResponse = productService.getProducts(offset, limit);

        // then
        verify(productMapper).selectProducts(offset, limit);
        assertEquals(limit - offset, productsResponse.getProducts().size());
        assertFalse(productsResponse.getProducts().isEmpty());
    }

    @Test
    @DisplayName("제품이 존재하지 않을 때 조회 성공 테스트 - 비어있는 리스트를 반환")
    public void getProductsEmptyTest() {

        // given
        int offset = 0;
        int limit = 5;

        List<Product> products = new ArrayList<>();
        when(productMapper.selectProducts(offset, limit)).thenReturn(products);

        // when
        ProductsResponse productsResponse = productService.getProducts(offset, limit);

        // then
        verify(productMapper).selectProducts(offset, limit);
        assertTrue(productsResponse.getProducts().isEmpty());
    }

    @Test
    @DisplayName("카테고리별 제품이 존재할 때 조회 성공 테스트 - offeet, limit, categoryId에 알맞는 리스트를 반환")
    public void getProductsByCategoryIdTest() {

        // given
        int offset = 0;
        int limit = 5;
        long categoryId = 1L;

        List<Product> products = new ArrayList<>();
        for (int i = offset; i < limit; i++) {
            products.add(
                    Product.builder()
                            .productCategoryId(categoryId)
                            .name("test")
                            .price(1000)
                            .imageUrl("test")
                            .description("test")
                            .abv(1.0)
                            .capacity(350)
                            .build());
        }
        when(productMapper.selectProductsByCategoryId(offset, limit, categoryId))
                .thenReturn(products);

        // when
        ProductsResponse productsResponse =
                productService.getProductsByCategoryId(offset, limit, categoryId);

        // then
        verify(productMapper).selectProductsByCategoryId(offset, limit, categoryId);
        assertEquals(limit - offset, productsResponse.getProducts().size());
        assertFalse(productsResponse.getProducts().isEmpty());

        for (Product product : products) {
            assertEquals(categoryId, product.getProductCategoryId());
        }
    }

    @Test
    @DisplayName("카테고리별 제품이 존재하지 않을 때 조회 성공 테스트 - 비어있는 리스트를 반환")
    public void getProductsByCategoryIdEmptyTest() {

        // given
        int offset = 0;
        int limit = 5;
        long categoryId = 1L;

        List<Product> products = new ArrayList<>();
        when(productMapper.selectProductsByCategoryId(offset, limit, categoryId))
                .thenReturn(products);

        // when
        ProductsResponse productsResponse =
                productService.getProductsByCategoryId(offset, limit, categoryId);

        // then
        verify(productMapper).selectProductsByCategoryId(offset, limit, categoryId);
        assertTrue(productsResponse.getProducts().isEmpty());
    }
}
