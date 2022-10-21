package com.flab.sooldama.domain.product.api;

import com.flab.sooldama.domain.product.dto.response.ProductResponse;
import com.flab.sooldama.domain.product.service.ProductService;
import java.util.List;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
입력 파라미터의 유효성 검증은 컨트롤러에서 최대한 처리해야하는데, 이 때 Spring에서는 AOP 기반으로 메소드의 요청을 가로채서
유효성 검증을 진행해주는 @Validated를 제공합니다.
@Validated 어노테이션은 특정 ArgumentResolver에 의해 유효성 검사가 진행되는 @Valid와 달리 AOP 기반으로 메소드 요청을
인터셉터하여 처리합니다. @Validated 어노테이션을 다음과 같이 클레스 레벨에 선언하게 되면 해당 클레스에 유효성 검증을 위한
인터셉터가 등록되며, 해당 클래스의 메소드들이 호출될 때 AOP의 포인트 컷으로써 요청을 가로채고 유효성검증을 진행합니다.
또한 @Validated 어노테이션은 자바 진영 스펙인 @Valid와 달리 스프링 프레임워크에서 제공하는 어노테이션 기능입니다.
 */

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
    public ResponseEntity<List<ProductResponse>> getProducts(
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "20") Integer limit,
            @RequestParam(required = false) Long categoryId) {

        List<ProductResponse> productsResponse =
                (categoryId == null)
                        ? productService.getProducts(offset, limit)
                        : productService.getProductsByCategoryId(offset, limit, categoryId);

        return ResponseEntity.ok().body(productsResponse);
    }
}
