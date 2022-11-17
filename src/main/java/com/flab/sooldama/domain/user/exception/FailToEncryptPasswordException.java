package com.flab.sooldama.domain.user.exception;

public class FailToEncryptPasswordException extends RuntimeException {

	public FailToEncryptPasswordException(String message) {
		super(message);
	}
}
