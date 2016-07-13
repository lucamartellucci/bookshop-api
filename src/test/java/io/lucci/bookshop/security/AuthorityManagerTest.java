package io.lucci.bookshop.security;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.mock.env.MockPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.lucci.bookshop.model.User;
import io.lucci.bookshop.security.AuthorityManager;
import io.lucci.bookshop.security.SimpleUserDetails;

@RunWith ( SpringJUnit4ClassRunner.class )
@ContextConfiguration(
		initializers = AuthorityManagerTest.PropertyMockingApplicationContextInitializer.class, 
		classes=AuthorityManagerTestConfig.class)

public class AuthorityManagerTest {
	
	@Autowired
	private AuthorityManager authorityManager;
	
	private static final String BOOKS_GET="BOOKS_GET";
	private static final String NOT_EXISTING_FUNCTION="NOT_EXISTING_FUNCTION";
	
	
	@Test
	public void testGetAuthoritiesFor() throws Exception {
		assertThat(authorityManager.getAuthoritiesFor(BOOKS_GET),containsInAnyOrder("ROLE_USER","ROLE_ADMIN"));
		assertThat(authorityManager.getAuthoritiesFor(BOOKS_GET),containsInAnyOrder("ROLE_USER","ROLE_ADMIN"));
		
		try {
			authorityManager.getAuthoritiesFor(NOT_EXISTING_FUNCTION);
			fail("It should fail for not existing function");
		} catch (NullPointerException e) {
			assertThat(e.getMessage(),is("Authorities not found for NOT_EXISTING_FUNCTION"));
			return;
		}
		fail("It shoul throws a nullpointer exception");
		
	}

	@Test
	public void testIsAuthorized() throws Exception {
		assertThat(authorityManager.isAuthorized(new SimpleUserDetails(new User().withRoles(Arrays.asList("ROLE_USER"))), BOOKS_GET), is(true));
		assertThat(authorityManager.isAuthorized(new SimpleUserDetails(new User().withRoles(Arrays.asList("ROLE_HACKER"))), BOOKS_GET), is(false));
	}
	
	public static class PropertyMockingApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	    @Override
	    public void initialize(ConfigurableApplicationContext applicationContext) {
	        MutablePropertySources propertySources = applicationContext.getEnvironment().getPropertySources();
	        MockPropertySource mockEnvVars = new MockPropertySource().withProperty(BOOKS_GET, "ROLE_USER,ROLE_ADMIN");
	        propertySources.replace(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, mockEnvVars);
	    }
	}	
	

}
