package com.flab.sooldama.domain.product.dto.response;

import com.flab.sooldama.domain.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponse {

	private Long id;
	private Long productCategoryId;
	private String name;
	private Integer price;
	private String imageUrl;
	private String description;
	private Double abv;
	private Integer capacity;

	public static ProductResponse of(Product product) {
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
