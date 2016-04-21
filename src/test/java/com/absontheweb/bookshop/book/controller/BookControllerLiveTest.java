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
import com.absontheweb.bookshop.test.base.AbstractBasicAuthLiveTest;

public class BookControllerLiveTest extends AbstractBasicAuthLiveTest {

	private static Logger logger = LoggerFactory.getLogger(BookControllerLiveTest.class);
	
	@Test
	public void testGetBook() throws Exception {
		
		addVar("id", "1");
		
		ResponseEntity<Book> responseEntity = restClient.exchange(
				BASE_API_URL.concat("/books/{id}"), 
				HttpMethod.GET, 
				new HttpEntity<Book>(basicAuthHeaders.get(Role.user)), 
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
	public void testGetBookNotExisting() throws Exception {
		addVar("id", "1001");
		try {
			restClient.exchange(
					BASE_API_URL.concat("/books/{id}"), 
					HttpMethod.GET, 
					new HttpEntity<Book>(basicAuthHeaders.get(Role.user)), 
					Book.class, urlVars() );
			fail();
		} catch (HttpClientErrorException e) {
			assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
		}
	}
	
	@Test
	public void testAddBookUnauthorized() throws Exception {
		
		Book book = buildBrandNewBook();
		
		HttpEntity<Book> requestEntity = new HttpEntity<Book>(book ,basicAuthHeaders.get(Role.user));
		
		try {
			restClient.exchange(
					BASE_API_URL.concat("/books"), 
					HttpMethod.POST, 
					requestEntity, 
					Book.class, urlVars() );
		
			fail("It should raise a unauthorized exception!");
		} catch (HttpClientErrorException e) {
			assertThat(e.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
		}
		
	}
	
	@Test
	public void testAddBook() throws Exception {
		
		Book book = buildBrandNewBook();
		//add also an existing co-author
		Author e = new Author();
		e.setId(1L);
		book.getAuthors().add(e);
		
		HttpEntity<Book> requestEntity = new HttpEntity<Book>(book ,basicAuthHeaders.get(Role.admin));
		
		ResponseEntity<Book> bookResponseEntity = restClient.exchange(
				BASE_API_URL.concat("/books"), 
				HttpMethod.POST, 
				requestEntity, 
				Book.class, urlVars() );
		
		Book savedBook = bookResponseEntity.getBody();
		
		assertThat(savedBook.getId(), is(notNullValue()));
		assertThat(savedBook.getDescription(),is(book.getDescription()));
		assertThat(savedBook.getAuthors().size(),is(2));
		assertThat(savedBook.getCurrency(),is(Currency.EUR));
		assertThat(savedBook.getIsbn(), is(book.getIsbn()));
	}

}
