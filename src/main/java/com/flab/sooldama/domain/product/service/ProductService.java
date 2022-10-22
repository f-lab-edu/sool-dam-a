package com.flab.sooldama.domain.product.service;

import com.flab.sooldama.domain.product.dao.ProductMapper;
import com.flab.sooldama.domain.product.domain.Product;
import com.flab.sooldama.domain.product.dto.response.ProductResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;

	public List<ProductResponse> getProducts(Integer offset, Integer limit, Long categoryId) {
		List<Product> products = productMapper.selectProducts(offset, limit, categoryId);
		List<ProductResponse> productResponses = new ArrayList<>();

		for (Product product : products) {
			productResponses.add(
				ProductResponse.builder()
					.id(product.getId())
					.productCategoryId(product.getProductCategoryId())
					.name(product.getName())
					.price(product.getPrice())
					.imageUrl(product.getImageUrl())
					.description(product.getDescription())
					.abv(product.getAbv())
					.capacity(product.getCapacity())
					.build());
		}

        return productResponses;
    }

    public ProductResponse getProductById(Long productId) {
        Product product = productMapper.selectProductById(productId);

        return ProductResponse.builder()
			.id(product.getId())
			.productCategoryId(product.getProductCategoryId())
			.name(product.getName())
			.price(product.getPrice())
			.imageUrl(product.getImageUrl())
			.description(product.getDescription())
			.abv(product.getAbv())
			.capacity(product.getCapacity())
			.build();
    }
}
