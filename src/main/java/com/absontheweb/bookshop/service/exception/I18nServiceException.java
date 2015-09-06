package com.absontheweb.bookshop.service.exception;


public class I18nServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public I18nServiceException() {
        super();
    }
    public I18nServiceException( final String s ) {
        super( s );
    }
    public I18nServiceException( final String s, final Throwable throwable ) {
        super( s, throwable );
    }
    public I18nServiceException( final Throwable throwable ) {
        super( throwable );
    }
}
