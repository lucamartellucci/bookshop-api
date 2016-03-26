package com.absontheweb.bookshop.book.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import com.absontheweb.bookshop.book.model.Author;
import com.absontheweb.bookshop.book.model.Book;
import com.absontheweb.bookshop.book.model.Currency;
import com.absontheweb.bookshop.test.base.AbstractLiveTest;

public class BookControllerLiveTest extends AbstractLiveTest {

	private static Logger logger = LoggerFactory.getLogger(BookControllerLiveTest.class);
	
	@Test
	public void testGetBook() throws Exception {
		
		addVar("id", "1");
		
		ResponseEntity<Book> responseEntity = restClient.exchange(
				BASE_API_URL.concat("/books/{id}"), 
				HttpMethod.GET, 
				new HttpEntity<Book>(securityHeaders), 
				Book.class, urlVars() );
		
		Book book = responseEntity.getBody();
		
		logger.debug("Retrieved book is [{}]", book);
		
		assertThat(book,is(notNullValue()));
    	assertThat(book.getTitle(), is(equalTo("Gomorra")));
    	assertThat(book.getDescription(), is(equalTo("Gomorra desc")));
    	assertThat(book.getCurrency(), is(equalTo(Currency.EUR)));
    	assertThat(book.getPrice(), is(equalTo(12.00)));
    	assertThat(book.getReleaseDate(), is(equalTo(LocalDate.of(2008,11,02))));
    	assertThat(book.getIsbn(), is(equalTo("1234567890")));
    	List<Author> authors = book.getAuthors();
		assertThat(authors.size(), is(1));
		Author author = authors.get(0);
		assertThat(author.getName(), is("Roberto"));
		assertThat(author.getSurname(), is("Saviano"));
	}
	
	@Test
	public void testGetBook_notExisting() throws Exception {
		addVar("id", "1001");
		try {
			restClient.exchange(
					BASE_API_URL.concat("/books/{id}"), 
					HttpMethod.GET, 
					new HttpEntity<Book>(securityHeaders), 
					Book.class, urlVars() );
			fail();
		} catch (HttpClientErrorException e) {
			assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
		}
	}

}
