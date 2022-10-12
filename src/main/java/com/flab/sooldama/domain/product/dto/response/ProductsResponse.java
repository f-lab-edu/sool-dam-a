package com.flab.sooldama.domain.product.dto.response;

import com.flab.sooldama.domain.product.domain.Product;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductsResponse {

    private List<Product> products;
    public static ProductsResponse from(List<Product> products) {
        return ProductsResponse.builder()
                .products(products)
                .build();
    }
}
