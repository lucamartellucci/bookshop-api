package com.absontheweb.bookshop.book.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.absontheweb.bookshop.application.Application;
import com.absontheweb.bookshop.application.MessageSourceConfig;
import com.absontheweb.bookshop.application.PersistenceConfig;
import com.absontheweb.bookshop.application.WebConfig;
import com.absontheweb.bookshop.book.model.Author;
import com.absontheweb.bookshop.book.model.Book;
import com.absontheweb.bookshop.book.model.Currency;

@RunWith(value=SpringJUnit4ClassRunner.class)
@WebIntegrationTest("server.port:18080")
@SpringApplicationConfiguration(classes={Application.class, MessageSourceConfig.class, PersistenceConfig.class, WebConfig.class})
@ActiveProfiles(profiles = { "dbtest" })
public class BookControllerLiveTest {

	private static Logger logger = LoggerFactory.getLogger(BookControllerLiveTest.class);
	
	private RestTemplate restClient;
	private String baseURL = "http://localhost:18080/api/";

    @Before
    public void setup() {
    	restClient = new RestTemplate();
    }

	@Test
	public void testGetBook() throws Exception {
		
		Map<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("id", "1");
		Book book = restClient.getForObject(baseURL.concat("/books/{id}"), Book.class, urlVariables);
		logger.debug("Retrieved book is [{}]", book);
		
		assertThat(book,is(notNullValue()));
    	assertThat(book.getTitle(), is(equalTo("Gomorra")));
    	assertThat(book.getDescription(), is(equalTo("Gomorra desc")));
    	assertThat(book.getCurrency(), is(equalTo(Currency.EUR)));
    	assertThat(book.getPrice(), is(equalTo(12.00)));
    	assertThat(book.getYear(), is(equalTo(2008)));
    	assertThat(book.getIsbn(), is(equalTo("1234567890")));
    	List<Author> authors = book.getAuthors();
		assertThat(authors.size(), is(1));
		Author author = authors.get(0);
		assertThat(author.getName(), is("Roberto"));
		assertThat(author.getSurname(), is("Saviano"));
	}
	
	@Test
	public void testGetBook_notExisting() throws Exception {
		Map<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("id", "1001");
		try {
			restClient.getForObject(baseURL.concat("/books/{id}"), Book.class, urlVariables);
			fail();
		} catch (HttpClientErrorException e) {
			assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
		}
	}
	



}
