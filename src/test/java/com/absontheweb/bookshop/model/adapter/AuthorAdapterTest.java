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
		Author author = authorAdapter.toAuthor(buildAuthorDBTO());
		
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
		for (int i = 0; i < 3; i++) {
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
		
		AuthorDBTO authorDBTO = buildAuthorDBTO();
		when(bookAdapter.toBooks(authorDBTO.getBooks())).thenReturn(Arrays.asList(new Book(), new Book(), new Book()));
		
		author = authorAdapter.toAuthor(authorDBTO, true);
		
		assertThat(author.getBooks(), is(notNullValue()));
		assertThat(author.getBooks().size(), is(3));
		
		verify(bookAdapter).toBooks(authorDBTO.getBooks());
	}

}
