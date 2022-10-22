package com.flab.sooldama.domain.product.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Product {

	private Long id;
	private Long productCategoryId;
	private String name;
	private Integer price;
	private String imageUrl;
	private String description;
	private Double abv;
	private Integer capacity;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
}