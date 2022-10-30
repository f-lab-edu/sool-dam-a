package com.flab.sooldama.global.exception;

import com.flab.sooldama.domain.user.exception.DuplicateEmailExistsException;
import com.flab.sooldama.domain.user.exception.NoSuchUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity.BodyBuilder;

/*
@Slf4j 어노테이션은 Lombok 어노테이션중 하나로, 좀 더 편리하게 로그를 찍을 수 있게 도와줍니다.
Slf4j는 다양한 로깅 프레임 워크에 대해 추상화역할을 하여 사용자가 배포시 원하는 로깅 프레임워크를 연결할 수 있도록 도와줍니다.
즉, DIP의 원칙을 따른 것이고, 최종 사용자가 배포시 원하는 로깅 프레임워크를 결정하고 사용해도 Slf4j는 인터페이스화 돼있기에,
slf4j를 의존하는 클라이언트 코드에서는 실제 구현을 몰라도 됩니다.

@ControllerAdvice 어노테이션은 컨트롤러에서 쓰이는 공통 기능들을 모듈화하여 전역적으로 쓰게 도와줍니다.
@ExceptionHandler 어노테이션은 @Controller Bean내에서 발생하는 특정 예외에 대한 전역적인 예외 처리를 할 수 있게 합니다.
동작 과정은 다음과 같습니다.
1. 디스패처 서블릿이 에러를 catch르 합니다.
2. 디스 패처 서블릿은 다양한 예외 처리기를 가지고 있고, 해당 에러를 처리할 수 있는 HandlerExceptionResolver가 에러를 처리합니다.
3. 전역적인 ControllerAdvice가 처리할 지, 지역적인 Controller 에서 처리할지 검사합니다.
4. ControllerAdvice의 ExceptionHandler로 처리가 가능한지 검사합니다.
5. ControllerAdvice의 ExceptionHandler 메소드를 invoke 하여 예외를 반환합니다. 이 때 리플렉션 API를 이용해서
ExceptionHandler의 구현 메소드를 호출해 처리한 에러를 반환하게 됩니다.
 */
@ControllerAdvice
@Slf4j
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

	@ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BodyBuilder> constraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
