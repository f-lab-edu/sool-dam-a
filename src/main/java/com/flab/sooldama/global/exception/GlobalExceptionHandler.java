package com.flab.sooldama.global.exception;

import com.flab.sooldama.domain.product.exception.OutOfRageForOffsetException;
import com.flab.sooldama.global.response.BasicResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
