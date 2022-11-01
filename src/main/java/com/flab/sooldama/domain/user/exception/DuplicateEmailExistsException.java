package com.flab.sooldama.domain.user.exception;

public class DuplicateEmailExistsException extends RuntimeException {

	public DuplicateEmailExistsException(String message) {
		super(message);
	}
}
