package com.flab.sooldama.domain.product.dto.response;

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
}
