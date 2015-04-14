package com.absontheweb.bookshop.service.exception;

public class BookServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public BookServiceException() {
	}

	public BookServiceException(String message) {
		super(message);
	}

	public BookServiceException(Throwable cause) {
		super(cause);
	}

	public BookServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public BookServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
