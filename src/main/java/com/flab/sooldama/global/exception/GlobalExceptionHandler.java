package com.flab.sooldama.global.exception;

import com.flab.sooldama.domain.product.exception.OutOfRageForOffsetException;
import com.flab.sooldama.global.response.BasicResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
@Slf4j 어노테이션은 Lombok 어노테이션중 하나로, 좀 더 편리하게 로그를 찍을 수 있게 도와줍니다.
@ControllerAdvice 어노테이션은 컨트롤러에서 쓰이는 공통 기능들을 모듈화하여 전역적으로 쓰게 도와줍니다.
@ExceptionHandler 어노테이션은 @Controller Bean내에서 발생하는 특정 예외에 대한 전역적인 예외 처리를 할 수 있게 합니다.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(OutOfRageForOffsetException.class)
    public ResponseEntity<BasicResponse<String>> outOfRageForOffsetException(
            OutOfRageForOffsetException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BasicResponse.fail(e.getMessage()));
    }
}
