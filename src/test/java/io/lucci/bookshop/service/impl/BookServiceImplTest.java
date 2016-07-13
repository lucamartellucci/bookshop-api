package io.lucci.bookshop.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import io.lucci.bookshop.book.model.Book;
import io.lucci.bookshop.book.model.adapter.AuthorAdapter;
import io.lucci.bookshop.book.model.adapter.BookAdapter;
import io.lucci.bookshop.model.PaginatorResult;
import io.lucci.bookshop.model.SimplePaginator;
import io.lucci.bookshop.persistence.model.BookDBTO;
import io.lucci.bookshop.persistence.repository.AuthorDBTORepository;
import io.lucci.bookshop.persistence.repository.BookDBTORepository;
import io.lucci.bookshop.service.exception.BookServiceException;
import io.lucci.bookshop.service.impl.BookServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {

	@Mock
	protected BookDBTORepository bookRepositoryMock;
	
	@Mock
	protected AuthorDBTORepository authorRepositoryMock;
	
	private BookAdapter bookAdapter;
	private AuthorAdapter authorAdapter;

	@InjectMocks
	protected BookServiceImpl bookService;

	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		
		bookAdapter = new BookAdapter();
		authorAdapter = new AuthorAdapter();
		bookAdapter.setAuthorAdapter(authorAdapter);
		authorAdapter.setBookAdapter(bookAdapter);
		
		bookService.setBookAdapter(bookAdapter);
		bookService.setAuthorAdapter(authorAdapter);
		
		reset(bookRepositoryMock);
		reset(authorRepositoryMock);
	}


	@Test
	public void testGetById() throws Exception {
		long bookId = 1L;
		long notExistingbookId = 2L;
		
		when(bookRepositoryMock.findOne(bookId)).thenReturn(buildBookDBTO(1L,"My First Book",11.3,"desc"));
		when(bookRepositoryMock.findOne(notExistingbookId)).thenThrow(new RuntimeException("Kaboom"));
		
		Book book = bookService.getById(bookId);
		
		assertThat(book,is(notNullValue()));
		assertThat(book.getId(),is(bookId));
		assertThat(book.getTitle(), is("My First Book"));
		assertThat(book.getPrice(), is(11.3));
		
		try {
			bookService.getById(notExistingbookId);
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof BookServiceException);
			assertTrue(e.getMessage().contains(""+notExistingbookId));
		}
		
		verify(bookRepositoryMock).findOne(bookId);
		verify(bookRepositoryMock).findOne(notExistingbookId);
	}								


	@Test
	public void testGetByPage() throws Exception {
		List<BookDBTO> bookDBTOs = Arrays.asList(
			buildBookDBTO(1L,"Book1",12.3,"desc1"),
			buildBookDBTO(2L,"Book2",6.3,"desc2")
		);
		
		PageImpl<BookDBTO> value = new PageImpl<>(bookDBTOs);
		PageRequest pageRequest = new PageRequest(1, 2);
		
		when(bookRepositoryMock.findAll()).thenReturn(bookDBTOs);
		when(bookRepositoryMock.findAll(pageRequest)).thenReturn(value);
		
		// call without pagination
		PaginatorResult<Book> books = bookService.getByPage(null);
		assertThat(books,is(notNullValue()));
		assertThat(books.getTotalPages(),is(1));
		assertThat(books.getCurrentPage(),is(0));
		assertThat(books.getTotalItems(),is(equalTo(books.getTotalItems())));
		assertThat(books.getTotalItems(),is(2L));
		assertThat(books.getResult().get(0).getId(),is(1L));
		
		
		books = bookService.getByPage(new SimplePaginator(1,2));
		assertThat(books,is(notNullValue()));
		assertThat(books.getTotalPages(),is(1));
		assertThat(books.getCurrentPage(),is(1));
		assertThat(books.getTotalItems(),is(equalTo(books.getTotalItems())));
		assertThat(books.getTotalItems(),is(2L));
		assertThat(books.getResult().get(0).getId(),is(1L));
		
		verify(bookRepositoryMock).findAll();
		verify(bookRepositoryMock).findAll(pageRequest);
	}

	@Test
	public void testSearchByTitle() throws Exception {
		when(bookRepositoryMock.findByTitle("Book1")).thenReturn(buildBookDBTO(1L,"Book1",12.3,"desc1"));
		when(bookRepositoryMock.findByTitle("Book2")).thenThrow(new RuntimeException("Kaboom"));
		Book book = bookService.searchByTitle("Book1");
		assertThat(book,is(notNullValue()));
		assertThat(book.getId(),is(1L));
		assertThat(book.getTitle(), is("Book1"));
		assertThat(book.getPrice(), is(12.3));
		
		try {
			bookService.searchByTitle("Book2");
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof BookServiceException);
			assertTrue(e.getMessage().contains("Book2"));
		}
		
		verify(bookRepositoryMock).findByTitle("Book1");
		verify(bookRepositoryMock).findByTitle("Book2");
	}
	
	
	
	
	private BookDBTO buildBookDBTO(long id, String title, double price, String desc) {
		BookDBTO bookDBTO = new BookDBTO();
		bookDBTO.setTitle(title);
		bookDBTO.setId(id);
		bookDBTO.setPrice(price);
		bookDBTO.setDescription(desc);
		return bookDBTO;
	}


	@Test
	public void testGetAll() throws Exception {
		List<BookDBTO> bookDBTOs = Arrays.asList(
			buildBookDBTO(1L,"Book1",12.3,"desc1"),
			buildBookDBTO(2L,"Book2",6.3,"desc2")
		);
		
		when(bookRepositoryMock.findAll()).thenReturn(bookDBTOs);
		
		List<Book> books = bookService.getAll();
		assertThat(books,is(notNullValue()));
		assertThat(books.size(),is(2));
		assertThat(books.get(0).getId(),is(1L));
		
		
		verify(bookRepositoryMock).findAll();
	}
	
	@Test
	public void testGetAllException() throws Exception {
		when(bookRepositoryMock.findAll()).thenThrow(new RuntimeException("Kaboom"));
		
		try {
			bookService.getAll();
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof BookServiceException);
			assertThat(e.getMessage(),is("Unable to load all books"));
		}
		
		verify(bookRepositoryMock).findAll();
	}

}
