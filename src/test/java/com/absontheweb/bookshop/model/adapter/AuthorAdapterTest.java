package com.absontheweb.bookshop.model.adapter;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.absontheweb.bookshop.model.Author;
import com.absontheweb.bookshop.model.Book;
import com.absontheweb.bookshop.model.BookBuilder;
import com.absontheweb.bookshop.model.Currency;
import com.absontheweb.bookshop.persistence.model.AuthorDBTO;
import com.absontheweb.bookshop.persistence.model.BookDBTO;

public class AuthorAdapterTest {
	
	@Mock
	BookAdapter bookAdapter;
	
	@InjectMocks
	AuthorAdapter authorAdapter;

	private SimpleDateFormat sdf;
	
	@Before public void initMocks() {
        MockitoAnnotations.initMocks(this);
        sdf = new SimpleDateFormat("dd-MM-yyyy");
    }
	
	@Test
	public void testToAuthorAuthorDBTO() throws Exception {
		Author author = authorAdapter.toAuthor(null);
		assertThat(author, is (nullValue()));
		
		author = authorAdapter.toAuthor(buildAuthorDBTO());
		
		assertThat(author.getName(), is("name"));
		assertThat(author.getSurname(), is("surname"));
		assertThat(author.getBirthplace(), is("city"));
		assertThat(sdf.format(author.getBorn()), is("01-01-1980"));
		assertThat(author.getBooks(), is(nullValue()));
	}

	private AuthorDBTO buildAuthorDBTO() throws ParseException {
		AuthorDBTO authorDBTO;
		authorDBTO = new AuthorDBTO();
		authorDBTO.setId(1L);
		authorDBTO.setName("name");
		authorDBTO.setSurname("surname");
		authorDBTO.setBorn(sdf.parse("01-01-1980"));
		authorDBTO.setBirthplace("city");
		authorDBTO.setBooks(new ArrayList<BookDBTO>());
		for (int i = 0; i < 2; i++) {
			authorDBTO.getBooks().add(buildBookDBTO(i));
		}
		return authorDBTO;
	}
	
	private BookDBTO buildBookDBTO(int id) {
		BookDBTO bookDBTO = new BookDBTO();
		bookDBTO.setId(new Long(id));
		bookDBTO.setCurrency(Currency.EUR);
		bookDBTO.setDescription("description"+id);
		bookDBTO.setIsbn("isbn"+id);
		bookDBTO.setPrice(3.5);
		bookDBTO.setTitle("title"+id);
		bookDBTO.setYear(id + 2000);
		return bookDBTO;
	}

	@Test
	public void testToAuthorAuthorDBTOBoolean() throws Exception {
		Author author = authorAdapter.toAuthor(buildAuthorDBTO(), false);

		assertThat(author.getName(), is("name"));
		assertThat(author.getSurname(), is("surname"));
		assertThat(author.getBirthplace(), is("city"));
		assertThat(sdf.format(author.getBorn()), is("01-01-1980"));
		assertThat(author.getBooks(), is(nullValue()));
		
		Book book1 = BookBuilder.book().withId(1L).withCurrency(Currency.EUR).withDescription("description1").withIsbn("isbn1").withPrice(3.5).withTitle("title1").withYear(2001).build();	
		Book book2 = BookBuilder.book().withId(2L).withCurrency(Currency.EUR).withDescription("description2").withIsbn("isbn2").withPrice(3.5).withTitle("title2").withYear(2002).build();	
		AuthorDBTO authorDBTO = buildAuthorDBTO();
		when(bookAdapter.toBooks(authorDBTO.getBooks())).thenReturn(Arrays.asList(book1,book2));
		
		author = authorAdapter.toAuthor(authorDBTO, true);
		
		assertThat(author.getBooks(), is(notNullValue()));
		assertThat(author.getBooks().size(), is(2));
		
		assertThat(author.getBooks().get(0).getId(),is(1L));
		assertThat(author.getBooks().get(0).getCurrency(),is(Currency.EUR));
		assertThat(author.getBooks().get(0).getDescription(),is("description1"));
		assertThat(author.getBooks().get(0).getIsbn(),is("isbn1"));
		assertThat(author.getBooks().get(0).getPrice(),is(3.5));
		assertThat(author.getBooks().get(0).getTitle(),is("title1"));
		assertThat(author.getBooks().get(0).getYear(),is(2001));

		assertThat(author.getBooks().get(1).getId(),is(2L));
		assertThat(author.getBooks().get(1).getCurrency(),is(Currency.EUR));
		assertThat(author.getBooks().get(1).getDescription(),is("description2"));
		assertThat(author.getBooks().get(1).getIsbn(),is("isbn2"));
		assertThat(author.getBooks().get(1).getPrice(),is(3.5));
		assertThat(author.getBooks().get(1).getTitle(),is("title2"));
		assertThat(author.getBooks().get(1).getYear(),is(2002));
		
		verify(bookAdapter).toBooks(authorDBTO.getBooks());
	}

}
