package io.lucci.bookshop.security;

public interface SecurityRules {

	public static final String BOOKS_ADD = "@authorityHandler.isAuthorized(principal, 'BOOKS_ADD')";
	public static final String BOOKS_GET_DETAIL = "@authorityHandler.isAuthorized(principal, 'BOOKS_GET_DETAIL')";
	public static final String BOOKS_GET = "@authorityHandler.isAuthorized(principal, 'BOOKS_GET')";

}