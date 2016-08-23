package io.lucci.bookshop.i18n.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import io.lucci.bookshop.i18n.model.Language;
import io.lucci.bookshop.test.base.AbstractBasicAuthLiveTest;


public class I18nControllerLiveTest extends AbstractBasicAuthLiveTest {

	@Test
	public void testGetMessages() throws Exception {
		addVar("language", "IT");
		
		ResponseEntity<Map<String,String>> responseEntity = restClient.exchange(
				BASE_API_URL.concat("/i18n/messages/{language}"),
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<Map<String,String>>() {}, 
				urlVars());
		
		Map<String,String> messages = responseEntity.getBody();

		assertThat(messages,is(notNullValue()));
		assertThat(messages.size(),is(greaterThan(2)));
    	assertThat(messages.get("languageName"), is("Italiano"));
	}
	
	
	@Test
	public void testGetLanguages() throws Exception {
		
		ResponseEntity<List<Language>> responseEntity = restClient.exchange(
				BASE_API_URL.concat("/i18n/languages"),
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Language>>() {}, urlVars());
		
		List<Language> languages = responseEntity.getBody();

		assertThat(languages,is(notNullValue()));
		assertThat(languages.size(),is(2));
		assertThat(languages, containsInAnyOrder(
				new Language("English","EN","http://localhost:8080/assets/EN_flag.png"),
				new Language("Italiano","IT","http://localhost:8080/assets/IT_flag.png"))
		);
	}
	

}
