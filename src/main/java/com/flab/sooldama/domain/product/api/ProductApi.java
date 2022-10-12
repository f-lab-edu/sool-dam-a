package com.flab.sooldama.domain.product.api;

import com.flab.sooldama.domain.product.dto.response.ProductsResponse;
import com.flab.sooldama.domain.product.exception.OutOfRageForOffsetException;
import com.flab.sooldama.domain.product.service.ProductService;
import com.flab.sooldama.global.response.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductApi {
    private final ProductService productService;

    @GetMapping("")
    public ResponseEntity<BasicResponse<ProductsResponse>> getProducts(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "20") Integer limit) {
        if (offset < 0) {
            throw new OutOfRageForOffsetException("offset이 0 이상이어야 합니다");
        }

        ProductsResponse productsResponse = productService.getProducts(offset, limit);
        return ResponseEntity.status(HttpStatus.OK).body(BasicResponse.success(productsResponse));
    }
}
