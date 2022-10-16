package com.flab.sooldama.domain.product.api;

import com.flab.sooldama.domain.product.dto.response.ProductsResponse;
import com.flab.sooldama.domain.product.service.ProductService;
import com.flab.sooldama.global.response.BasicResponse;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@Validated
@RequiredArgsConstructor
public class ProductApi {
    private final ProductService productService;

    /*
    @RequestParam 어노테이션은 쿼리스트링을 파라미터로 받을 수 있게 도와줍니다.
     */
    @GetMapping("")
    public ResponseEntity<BasicResponse<ProductsResponse>> getProducts(
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "20") Integer limit) {
        ProductsResponse productsResponse = productService.getProducts(offset, limit);
        return ResponseEntity.ok().body(BasicResponse.success(productsResponse));
    }
}
