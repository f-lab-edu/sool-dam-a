package com.flab.sooldama.domain.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.flab.sooldama.domain.product.dao.ProductMapper;
import com.flab.sooldama.domain.product.domain.Product;
import com.flab.sooldama.domain.product.dto.response.ProductsResponse;
import com.flab.sooldama.domain.product.service.ProductService;
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
    @DisplayName("offset과 limit를 활용하여 상품 조회")
    public void getProductsTest() {

        // given
        int offset = 1;
        int limit = 3;
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
    }
}
