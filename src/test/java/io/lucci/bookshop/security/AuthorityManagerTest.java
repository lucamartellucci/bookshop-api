package io.lucci.bookshop.security;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import io.lucci.bookshop.model.User;


public class AuthorityManagerTest {
	
	private AuthorityHandler authorityManager;
	
	private static final String BOOKS_GET="BOOKS_GET";
	private static final String NOT_EXISTING_FUNCTION="NOT_EXISTING_FUNCTION";
	
	@Before
	public void setUp() throws Exception {
		authorityManager = new AuthorityHandler();
		authorityManager.setAuthorities(ImmutableMap.of(
			"ROLE_USER", "BOOKS_GET,BOOKS_GET_DETAIL,USER_GET", 
			"ROLE_ADMIN", "BOOKS_GET,BOOKS_GET_DETAIL,USER_GET,BOOKS_ADD"
		));
		authorityManager.afterPropertiesSet();
	}
	
	
	@Test
	public void testGetAuthoritiesFor() throws Exception {
		assertThat(authorityManager.getAuthoritiesFor(BOOKS_GET),containsInAnyOrder("ROLE_USER","ROLE_ADMIN"));
		assertThat(authorityManager.getAuthoritiesFor(BOOKS_GET),containsInAnyOrder("ROLE_USER","ROLE_ADMIN"));
		assertThat(authorityManager.getAuthoritiesFor(NOT_EXISTING_FUNCTION),is(nullValue()));
	}

	@Test
	public void testIsAuthorized() throws Exception {
		assertThat(authorityManager.isAuthorized(new SimpleUserDetails(new User().withRoles(Arrays.asList("ROLE_USER"))), BOOKS_GET), is(true));
		assertThat(authorityManager.isAuthorized(new SimpleUserDetails(new User().withRoles(Arrays.asList("ROLE_HACKER"))), BOOKS_GET), is(false));
	}
	
	
	

}
