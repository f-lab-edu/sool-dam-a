package com.flab.sooldama.domain.product.dao;

import static org.junit.jupiter.api.Assertions.*;

import com.flab.sooldama.domain.product.domain.Product;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ProductMapperTest {

    @Autowired private ProductMapper productMapper;

    @Test
    @DisplayName("전체 제품 조회 테스트")
    public void productSelectTest() {

        List<Product> products = productMapper.selectProducts(0, 1);

        assertEquals(1L, products.get(0).getProductCategoryId());
        assertEquals("백련 미스티 살균 막걸리", products.get(0).getName());
        assertEquals(4500, products.get(0).getPrice());
        assertEquals("연꽃이 들어간 살균 막걸리", products.get(0).getDescription());
        assertEquals(7.0, products.get(0).getAbv());
        assertEquals(375, products.get(0).getCapacity());
    }
}
