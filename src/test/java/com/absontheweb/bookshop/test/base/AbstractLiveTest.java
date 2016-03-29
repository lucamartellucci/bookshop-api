package com.absontheweb.bookshop.test.base;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.absontheweb.bookshop.application.Application;
import com.absontheweb.bookshop.application.MessageSourceConfig;
import com.absontheweb.bookshop.application.PersistenceConfig;
import com.absontheweb.bookshop.application.SecurityConfig;
import com.absontheweb.bookshop.application.WebConfig;
import com.absontheweb.bookshop.book.controller.AbstractBookTest;
import com.absontheweb.bookshop.security.util.SecurityUtils;


@RunWith(value=SpringJUnit4ClassRunner.class)
@WebIntegrationTest("server.port=0")
@SpringApplicationConfiguration(classes={Application.class, MessageSourceConfig.class,
			PersistenceConfig.class, WebConfig.class, SecurityConfig.class})
@ActiveProfiles(profiles = { "dbtest" })
public abstract class AbstractLiveTest extends AbstractBookTest {
	
	protected static final String USER_PASSWORD = "admin";
	protected static final String USER_LOGIN = "luca";
	protected static final String ADMIN_PASSWORD = "admin";
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
    public void setup() {
    	restClient = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    	urlVariables = new HashMap<>();
    	basicAuthHeaders = new HashMap<>();
    	
    	basicAuthHeaders.put(Role.user, SecurityUtils.createHeaders(USER_LOGIN, USER_PASSWORD));
    	basicAuthHeaders.put(Role.admin, SecurityUtils.createHeaders(ADMIN_LOGIN, ADMIN_PASSWORD));
    	
    	urlVariables.put("port", String.valueOf(port));
    }
    
	
	
	protected void addVar(String key, String value) {
		urlVariables.put(key, value);
	}

	protected Map<String, String> urlVars() {
		return this.urlVariables;
	}

}
