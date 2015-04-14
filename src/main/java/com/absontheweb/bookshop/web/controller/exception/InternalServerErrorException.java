package com.absontheweb.bookshop.web.controller.exception;

public class InternalServerErrorException extends Exception {

	private static final long serialVersionUID = 1447284189245097237L;

	public InternalServerErrorException() {
	}

	public InternalServerErrorException(String message) {
		super(message);
	}

	public InternalServerErrorException(Throwable cause) {
		super(cause);
	}

	public InternalServerErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public InternalServerErrorException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
