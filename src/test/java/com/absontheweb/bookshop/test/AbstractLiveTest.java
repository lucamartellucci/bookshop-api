package com.absontheweb.bookshop.test;

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


@RunWith(value=SpringJUnit4ClassRunner.class)
@WebIntegrationTest("server.port=0")
@SpringApplicationConfiguration(classes={Application.class, MessageSourceConfig.class,
			PersistenceConfig.class, WebConfig.class, SecurityConfig.class})
@ActiveProfiles(profiles = { "dbtest" })
public abstract class AbstractLiveTest {
	
	protected static final String PASSWORD = "password";
	protected static final String LOGIN = "luca";
	protected static final String BASE_API_URL = "http://localhost:{port}/bookshop/api/";
	
	protected HttpHeaders securityHeaders;
	protected RestTemplate restClient;
	private Map<String, String> urlVariables;
	protected @Value("${local.server.port}") int port;

	
    @Before
    public void setup() {
    	restClient = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    	urlVariables = new HashMap<>();
    	securityHeaders = SecurityUtils.createHeaders(LOGIN, PASSWORD);
    	urlVariables.put("port", String.valueOf(port));
    }
    
	
	
	protected void addVar(String key, String value) {
		urlVariables.put(key, value);
	}

	protected Map<String, String> urlVars() {
		return this.urlVariables;
	}

}
