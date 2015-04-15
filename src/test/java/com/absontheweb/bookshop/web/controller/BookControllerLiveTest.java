package com.absontheweb.bookshop.web.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.absontheweb.bookshop.model.Author;
import com.absontheweb.bookshop.model.AuthorBuilder;
import com.absontheweb.bookshop.model.Book;
import com.absontheweb.bookshop.model.BookBuilder;
import com.absontheweb.bookshop.model.Currency;

public class BookControllerLiveTest {

	private static Logger logger = LoggerFactory.getLogger(BookControllerLiveTest.class);
	
	private RestTemplate restClient;
	private String baseURL = "http://localhost:8080/api/";

    @Before
    public void setup() {
    	restClient = new RestTemplate();
    }

	@Test
	public void testGetAllBooks_OK() throws Exception {
	}
	
	@Test
	public void testGetAllBooks_InternalServerError() throws Exception {
		
	}
	
	@Test
	public void testGetBook() throws Exception {
		
		Map<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("BOOK_ID", "1");
		Book book = restClient.getForObject(baseURL.concat("/books/{BOOK_ID}"), Book.class, urlVariables);
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
	
	}
	
	/*
	 * UTILITY METHODS
	 */
	private Author buildAuthor(Long id, Date born, Date died) {
		Author author = AuthorBuilder.author()
				.withId(id)
				.withBirthplace("bithplace"+id)
				.withName("name"+id)
				.withSurname("surname"+id)
				.withBorn(born)
				.withDied(died)
				.build();
		return author;
	}
	
	private Book buildBook(Long id, Currency currency, Double price, List<Author> authors) {
		Book book = BookBuilder.book()
				.withId(id)
				.withTitle("title"+id)
				.withDescription("desc"+id)
				.withCurrency(currency)
				.withIsbn("isbn1")
				.withPrice(price)
				.withAuthors(authors)
				.build();
		return book;
	}


}
