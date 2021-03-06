package com.tenchael.toauth2.client.commons;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotFoundException(Throwable cause) {
		super(cause);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException() {
	}
}
