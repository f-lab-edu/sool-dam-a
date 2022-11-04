package com.flab.sooldama.domain.user.exception;

public class PasswordNotMatchException extends RuntimeException {
	public PasswordNotMatchException(String message) {
		super(message);
	}
}
