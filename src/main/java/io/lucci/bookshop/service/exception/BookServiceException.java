package io.lucci.bookshop.service.exception;

public class BookServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public BookServiceException() {
	}
	
	public BookServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
