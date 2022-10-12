package com.flab.sooldama.domain.product.service;

import com.flab.sooldama.domain.product.dao.ProductMapper;
import com.flab.sooldama.domain.product.domain.Product;
import com.flab.sooldama.domain.product.dto.response.ProductsResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;

    public ProductsResponse getProducts(Integer offset, Integer limit) {
        List<Product> products = productMapper.selectProducts(offset, limit);
        return ProductsResponse.from(products);
    }
}
