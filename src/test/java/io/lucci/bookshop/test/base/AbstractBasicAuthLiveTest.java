package io.lucci.bookshop.test.base;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import io.lucci.bookshop.application.Application;
import io.lucci.bookshop.application.BasicAuthSecurityConfig;
import io.lucci.bookshop.application.MessageSourceConfig;
import io.lucci.bookshop.application.PersistenceConfig;
import io.lucci.bookshop.application.WebConfig;
import io.lucci.bookshop.book.controller.AbstractBookTest;
import io.lucci.bookshop.security.util.SecurityUtils;


@RunWith(value=SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = { "dbtest" })
@SpringBootTest(
	webEnvironment=WebEnvironment.RANDOM_PORT, 
	classes={
		Application.class, 
		MessageSourceConfig.class,
		PersistenceConfig.class, 
		WebConfig.class, 
		BasicAuthSecurityConfig.class
	}
)
public abstract class AbstractBasicAuthLiveTest extends AbstractBookTest {
	
	protected static final String USER_PASSWORD = "password01";
	protected static final String USER_LOGIN = "luca";
	protected static final String ADMIN_PASSWORD = "password01";
	protected static final String ADMIN_LOGIN = "admin";
	protected static final String BASE_API_URL = "http://localhost:{port}/bookshop/api/";
	
	protected RestTemplate restClient;
	private Map<String, String> urlVariables;
	protected @Value("${local.server.port}") int port;
	
	protected Map<Role,HttpHeaders> basicAuthHeaders;
	
	public static enum Role {
		admin,user
	}

	
    @Before
    public void setUp() {
    	restClient = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    	urlVariables = new HashMap<>();
    	basicAuthHeaders = new HashMap<>();
    	
    	basicAuthHeaders.put(Role.user, SecurityUtils.createBasicAuthHeaders(USER_LOGIN, USER_PASSWORD));
    	basicAuthHeaders.put(Role.admin, SecurityUtils.createBasicAuthHeaders(ADMIN_LOGIN, ADMIN_PASSWORD));
    	
    	urlVariables.put("port", String.valueOf(port));
    }
    
	
	
	protected void addVar(String key, String value) {
		urlVariables.put(key, value);
	}

	protected Map<String, String> urlVars() {
		return this.urlVariables;
	}

}
