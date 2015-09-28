package com.absontheweb.bookshop.i18n.controller;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.absontheweb.bookshop.application.Application;
import com.absontheweb.bookshop.application.MessageSourceConfig;
import com.absontheweb.bookshop.application.PersistenceConfig;
import com.absontheweb.bookshop.application.WebConfig;
import com.absontheweb.bookshop.i18n.model.Language;
import com.google.common.collect.ImmutableMap;

@RunWith(value=SpringJUnit4ClassRunner.class)
@WebIntegrationTest("server.port:18080")
@SpringApplicationConfiguration(classes={Application.class, MessageSourceConfig.class, PersistenceConfig.class, WebConfig.class})
@ActiveProfiles(profiles = { "dbtest" })
public class I18nControllerLiveTest {

	private RestTemplate restClient;
	private String baseURL = "http://localhost:18080/api/";

    @Before
    public void setup() {
    	restClient = new RestTemplate();
    }

	@Test
	public void testGetMessages() throws Exception {
		
		ResponseEntity<Map<String,String>> responseEntity = restClient.exchange(
				baseURL.concat("/i18n/messages/{language}"),
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<Map<String,String>>() {}, 
				ImmutableMap.of("language", "IT"));
		
		Map<String,String> messages = responseEntity.getBody();

		assertThat(messages,is(notNullValue()));
		assertThat(messages.size(),is(greaterThan(2)));
    	assertThat(messages.get("languageName"), is("Italiano"));
	}
	
	
	@Test
	public void testGetLanguages() throws Exception {
		
		ResponseEntity<List<Language>> responseEntity = restClient.exchange(
				baseURL.concat("/i18n/languages"),
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<Language>>() {});
		
		List<Language> languages = responseEntity.getBody();

		assertThat(languages,is(notNullValue()));
		assertThat(languages.size(),is(2));
		Language lang = languages.get(0);
    	assertThat(lang.getName(), is("English"));
    	assertThat(lang.getLocale(), is("EN"));
    	assertThat(lang.getFlagUrl(), is("http://localhost:8080/assets/EN_flag.png"));
		lang = languages.get(1);
    	assertThat(lang.getName(), is("Italiano"));
    	assertThat(lang.getLocale(), is("IT"));
    	assertThat(lang.getFlagUrl(), is("http://localhost:8080/assets/IT_flag.png"));
	}
	

}
