package com.flab.sooldama.global.exception;

import com.flab.sooldama.domain.user.exception.DuplicateEmailExistsException;
import com.flab.sooldama.domain.user.exception.NoSuchUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailExistsException.class)
    public ResponseEntity<HttpStatus> handleDuplicateEmailExistsException(
            DuplicateEmailExistsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<HttpStatus> handleNoSuchUserException(
            NoSuchUserException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
