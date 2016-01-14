package com.absontheweb.bookshop.book.model.adapter;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.absontheweb.bookshop.book.model.Author;
import com.absontheweb.bookshop.book.model.Book;
import com.absontheweb.bookshop.book.model.BookBuilder;
import com.absontheweb.bookshop.book.model.Currency;
import com.absontheweb.bookshop.persistence.model.AuthorDBTO;
import com.absontheweb.bookshop.persistence.model.BookDBTO;

public class AuthorAdapterTest {
	
	@Mock
	BookAdapter bookAdapter;
	
	@InjectMocks
	AuthorAdapter authorAdapter;

	@Before public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testToAuthorAuthorDBTO() throws Exception {
		Author author = authorAdapter.toAuthor(null);
		assertThat(author, is (nullValue()));
		
		author = authorAdapter.toAuthor(buildAuthorDBTO(1));
		
		assertThat(author.getName(), is("name1"));
		assertThat(author.getSurname(), is("surname1"));
		assertThat(author.getBirthplace(), is("city"));
		assertThat(author.getBorn().format(DateTimeFormatter.ISO_DATE), is("1980-01-01"));
		assertThat(author.getBooks(), is(nullValue()));
	}

	@Test
	public void testToAuthorAuthorDBTOBoolean() throws Exception {
		Author author = authorAdapter.toAuthor(buildAuthorDBTO(1), false);

		assertThat(author.getName(), is("name1"));
		assertThat(author.getSurname(), is("surname1"));
		assertThat(author.getBirthplace(), is("city"));
		assertThat(author.getBorn().format(DateTimeFormatter.ISO_DATE), is("1980-01-01"));
		assertThat(author.getBooks(), is(nullValue()));
		
		Book book1 = BookBuilder.book().withId(1L).withCurrency(Currency.EUR).withDescription("description1").withIsbn("isbn1").withPrice(3.5).withTitle("title1").withReleaseDate(LocalDate.of(2001, 01, 01)).build();	
		Book book2 = BookBuilder.book().withId(2L).withCurrency(Currency.EUR).withDescription("description2").withIsbn("isbn2").withPrice(3.5).withTitle("title2").withReleaseDate(LocalDate.of(2002, 01, 01)).build();	
		AuthorDBTO authorDBTO = buildAuthorDBTO(1);
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
		assertThat(author.getBooks().get(0).getReleaseDate(),is(LocalDate.of(2001, 01, 01)));

		assertThat(author.getBooks().get(1).getId(),is(2L));
		assertThat(author.getBooks().get(1).getCurrency(),is(Currency.EUR));
		assertThat(author.getBooks().get(1).getDescription(),is("description2"));
		assertThat(author.getBooks().get(1).getIsbn(),is("isbn2"));
		assertThat(author.getBooks().get(1).getPrice(),is(3.5));
		assertThat(author.getBooks().get(1).getTitle(),is("title2"));
		assertThat(author.getBooks().get(1).getReleaseDate(),is(LocalDate.of(2002, 01, 01)));
		
		verify(bookAdapter).toBooks(authorDBTO.getBooks());
	}
	
	@Test
	public void testToAuthorsListAuthorDBTO() throws Exception {
		
		List<Author> authors = authorAdapter.toAuthors(null,true);
		assertThat(authors, is(nullValue()));
		
		authors = authorAdapter.toAuthors(Arrays.asList(buildAuthorDBTO(1), buildAuthorDBTO(2)));
		assertThat(authors.size(),is(2));
		
		assertThat(authors.get(0).getName(), is("name1"));
		assertThat(authors.get(0).getSurname(), is("surname1"));
		assertThat(authors.get(0).getBirthplace(), is("city"));
		assertThat(authors.get(0).getBooks(), is(nullValue()));

		assertThat(authors.get(1).getName(), is("name2"));
		assertThat(authors.get(1).getSurname(), is("surname2"));
		assertThat(authors.get(1).getBirthplace(), is("city"));
		assertThat(authors.get(1).getBooks(), is(nullValue()));
	}
	
	@Test
	public void testToAuthorsListAuthorDBTOBoolean() throws Exception {
		
		Book book1 = BookBuilder.book().withId(1L).withCurrency(Currency.EUR).withDescription("description1").withIsbn("isbn1").withPrice(3.5).withTitle("title1").withReleaseDate(LocalDate.of(2001, 01, 01)).build();	
		Book book2 = BookBuilder.book().withId(2L).withCurrency(Currency.EUR).withDescription("description2").withIsbn("isbn2").withPrice(3.5).withTitle("title2").withReleaseDate(LocalDate.of(2002, 01, 01)).build();	
		
		AuthorDBTO authorDBTO1 = buildAuthorDBTO(1);
		AuthorDBTO authorDBTO2 = buildAuthorDBTO(2);
		
		when(bookAdapter.toBooks(authorDBTO1.getBooks()))
			.thenReturn(Arrays.asList(book1,book2));
		when(bookAdapter.toBooks(authorDBTO2.getBooks()))
			.thenReturn(Arrays.asList(book1,book2));
		
		
		List<Author> authors = authorAdapter.toAuthors(Arrays.asList(authorDBTO1, authorDBTO2), true);
		assertThat(authors.size(),is(2));
		
		assertThat(authors.get(0).getName(), is("name1"));
		assertThat(authors.get(0).getSurname(), is("surname1"));
		assertThat(authors.get(0).getBirthplace(), is("city"));
		assertThat(authors.get(0).getBooks().size(), is(2));

		assertThat(authors.get(1).getName(), is("name2"));
		assertThat(authors.get(1).getSurname(), is("surname2"));
		assertThat(authors.get(1).getBirthplace(), is("city"));
		assertThat(authors.get(1).getBooks().size(), is(2));
		
		verify(bookAdapter).toBooks(authorDBTO1.getBooks());
		verify(bookAdapter).toBooks(authorDBTO2.getBooks());
	}
	
	
	private AuthorDBTO buildAuthorDBTO(int id) throws ParseException {
		AuthorDBTO authorDBTO;
		authorDBTO = new AuthorDBTO();
		authorDBTO.setId(new Long(id));
		authorDBTO.setName("name"+id);
		authorDBTO.setSurname("surname"+id);
		authorDBTO.setBorn( LocalDate.parse("1980-01-01", DateTimeFormatter.ISO_DATE));
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
		bookDBTO.setReleaseDate(LocalDate.of(id + 2000, 01, 01));
		return bookDBTO;
	}

}
