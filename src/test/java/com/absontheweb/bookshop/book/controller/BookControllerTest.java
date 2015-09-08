package com.absontheweb.bookshop.book.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.absontheweb.bookshop.application.MessageSourceConfig;
import com.absontheweb.bookshop.book.model.Author;
import com.absontheweb.bookshop.book.model.AuthorBuilder;
import com.absontheweb.bookshop.book.model.Book;
import com.absontheweb.bookshop.book.model.BookBuilder;
import com.absontheweb.bookshop.book.model.Currency;
import com.absontheweb.bookshop.controller.exception.ErrorCode;
import com.absontheweb.bookshop.model.PaginatorResult;
import com.absontheweb.bookshop.model.SimplePaginator;
import com.absontheweb.bookshop.service.BookService;
import com.absontheweb.bookshop.service.exception.BookServiceException;
import com.google.common.collect.ImmutableMap;

import config.BookControllerTestConfig;

@RunWith ( SpringJUnit4ClassRunner.class )
@ContextConfiguration ( classes = { BookControllerTestConfig.class, MessageSourceConfig.class } )
@WebAppConfiguration
public class BookControllerTest {
	
	private static final SimplePaginator ALL = null;
	private static final SimplePaginator PAGE_1 = new SimplePaginator(0, 10);
	
    @Autowired
    private WebApplicationContext wac;
    
    @Autowired
    private BookService bookService;

    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup( this.wac ).build();
        reset(bookService);
    }

	@Test
	public void testGetBooks_OK() throws Exception {
		
		// prepare objects returned by the bookservice
		List<Author> authors = Arrays.asList(buildAuthor(1L, toDate("1978-09-25"), null));
		Book book1 = buildBook(1L, Currency.EUR, 10.0, authors);
		Book book2 = buildBook(2L, Currency.USD, 12.0, authors);
		List<Book> books = Arrays.asList(book1, book2);
		
		PaginatorResult<Book> paginatedBooks = new PaginatorResult<Book>();
		paginatedBooks.setCurrentPage(0);
		paginatedBooks.setPageSize(books.size());
		paginatedBooks.setResult(books);
		paginatedBooks.setTotalItems(books.size());
		paginatedBooks.setTotalPages(1);
		
		// program bookService mock to return a collection of books
		when(bookService.getBooks(ALL)).thenReturn(paginatedBooks);
    	
        this.mockMvc.perform( get( "/api/books" ).accept( MediaType.parseMediaType( "application/json;charset=UTF-8" ) ) )
	        .andExpect( status().isOk() )
	        .andExpect( content().contentType( "application/json;charset=UTF-8" ) )
	        .andDo( print() )
	        .andExpect( jsonPath( "$.result", hasSize(2)) )
	        .andExpect( jsonPath( "$.result[0].id" ).value( book1.getId().intValue() ) )
	        .andExpect( jsonPath( "$.result[0].title" ).value( book1.getTitle() ) )
	        .andExpect( jsonPath( "$.result[0].description" ).value( book1.getDescription() ) )
	        .andExpect( jsonPath( "$.result[0].currency" ).value( book1.getCurrency().toString() ) )
	        .andExpect( jsonPath( "$.result[0].isbn" ).value( book1.getIsbn() ) )
	        .andExpect( jsonPath( "$.result[0].price" ).value( book1.getPrice() ) )
	        .andExpect( jsonPath( "$.result[0].authors", hasSize(1) ))
	        .andExpect( jsonPath( "$.result[0].authors[0].name" ).value( book1.getAuthors().get(0).getName() ))
	        .andExpect( jsonPath( "$.result[0].authors[0].surname" ).value( book1.getAuthors().get(0).getSurname() ))
	        .andExpect( jsonPath( "$.result[0].authors[0].born" ).value( book1.getAuthors().get(0).getBorn().format(DateTimeFormatter.ISO_DATE) ))
	        .andExpect( jsonPath( "$.result[0].authors[0].birthplace" ).value( book1.getAuthors().get(0).getBirthplace() ));
	        
        verify(bookService).getBooks(ALL);
	}
	
	@Test
	public void testGetBooks_paged_OK() throws Exception {
		
		// prepare objects returned by the bookservice
		List<Author> authors = Arrays.asList(buildAuthor(1L, toDate("1978-09-25"), null));
		Book book1 = buildBook(1L, Currency.EUR, 10.0, authors);
		Book book2 = buildBook(2L, Currency.USD, 12.0, authors);
		List<Book> books = Arrays.asList(book1, book2);
		
		PaginatorResult<Book> paginatedBooks = new PaginatorResult<Book>();
		paginatedBooks.setCurrentPage(0);
		paginatedBooks.setPageSize(books.size());
		paginatedBooks.setResult(books);
		paginatedBooks.setTotalItems(books.size());
		paginatedBooks.setTotalPages(1);
		
		// program bookService mock to return a collection of books
		when(bookService.getBooks(PAGE_1)).thenReturn(paginatedBooks);
    	
        this.mockMvc.perform( get( "/api/books?page=0&size=10" ).accept( MediaType.parseMediaType( "application/json;charset=UTF-8" ) ) )
	        .andExpect( status().isOk() )
	        .andExpect( content().contentType( "application/json;charset=UTF-8" ) )
	        .andDo( print() )
	        .andExpect( jsonPath( "$.result", hasSize(2)) )
	        .andExpect( jsonPath( "$.result[0].id" ).value( book1.getId().intValue() ) )
	        .andExpect( jsonPath( "$.result[0].title" ).value( book1.getTitle() ) )
	        .andExpect( jsonPath( "$.result[0].description" ).value( book1.getDescription() ) )
	        .andExpect( jsonPath( "$.result[0].currency" ).value( book1.getCurrency().toString() ) )
	        .andExpect( jsonPath( "$.result[0].isbn" ).value( book1.getIsbn() ) )
	        .andExpect( jsonPath( "$.result[0].price" ).value( book1.getPrice() ) )
	        .andExpect( jsonPath( "$.result[0].authors", hasSize(1) ))
	        .andExpect( jsonPath( "$.result[0].authors[0].name" ).value( book1.getAuthors().get(0).getName() ))
	        .andExpect( jsonPath( "$.result[0].authors[0].surname" ).value( book1.getAuthors().get(0).getSurname() ))
	        .andExpect( jsonPath( "$.result[0].authors[0].born" ).value( book1.getAuthors().get(0).getBorn().format(DateTimeFormatter.ISO_DATE)))
	        .andExpect( jsonPath( "$.result[0].authors[0].birthplace" ).value( book1.getAuthors().get(0).getBirthplace() ));
	        
        verify(bookService).getBooks(PAGE_1);
	}
	
	
	@Test
	public void testGetBooks_InternalServerError() throws Exception {
		
		// program bookService mock to throw an exception
		when(bookService.getBooks(ALL)).thenThrow(new BookServiceException());
    	
        this.mockMvc.perform( get( "/api/books" ).accept( MediaType.parseMediaType( "application/json;charset=UTF-8" ) ) )
	        .andExpect( status().isInternalServerError() )
	        .andExpect( content().contentType( "application/json;charset=UTF-8" ) )
	        .andDo( print() )
	        .andExpect( jsonPath("$.code").value("GENERIC_ERROR")) ;

        verify(bookService).getBooks(ALL);
	}
	
	@Test
	public void testGetBook() throws Exception {
		// prepare objects returned by the bookservice
		List<Author> authors = Arrays.asList(buildAuthor(1L, toDate("1978-09-25"), null));
		Long bookId = 2L;
		Book book = buildBook(bookId, Currency.EUR, 10.0, authors);
		
		// program bookService mock to return the book with id bookId
		when(bookService.getBookById(bookId)).thenReturn(book);
    	
        this.mockMvc.perform( get( "/api/books/" + bookId ).accept( MediaType.parseMediaType( "application/json;charset=UTF-8" ) ) )
	        .andExpect( status().isOk() )
	        .andExpect( content().contentType( "application/json;charset=UTF-8" ) )
	        .andDo( print() )
	        .andExpect( jsonPath( "$.id" ).value( book.getId().intValue() ) )
	        .andExpect( jsonPath( "$.title" ).value( book.getTitle() ) )
	        .andExpect( jsonPath( "$.description" ).value( book.getDescription() ) )
	        .andExpect( jsonPath( "$.currency" ).value( book.getCurrency().toString() ) )
	        .andExpect( jsonPath( "$.isbn" ).value( book.getIsbn() ) )
	        .andExpect( jsonPath( "$.price" ).value( book.getPrice() ) )
	        .andExpect( jsonPath( "$.authors", hasSize(1) ))
	        .andExpect( jsonPath( "$.authors[0].name" ).value( book.getAuthors().get(0).getName() ))
	        .andExpect( jsonPath( "$.authors[0].surname" ).value( book.getAuthors().get(0).getSurname() ))
	        .andExpect( jsonPath( "$.authors[0].born" ).value( book.getAuthors().get(0).getBorn().format(DateTimeFormatter.ISO_DATE)))
	        .andExpect( jsonPath( "$.authors[0].birthplace" ).value( book.getAuthors().get(0).getBirthplace() ));
	        
        verify(bookService).getBookById(bookId);
	}
	
	@Test
	public void testGetBook_InternalServerError() throws Exception {
		// prepare objects returned by the bookservice
		Long bookId = 2L;
		
		// program bookService mock to return the book with id bookId
		when(bookService.getBookById(bookId)).thenThrow(new BookServiceException());
    	
        this.mockMvc.perform( get( "/api/books/" + bookId ).accept( MediaType.parseMediaType( "application/json;charset=UTF-8" ) ) )
	        .andExpect( status().isInternalServerError() )
	        .andExpect( content().contentType( "application/json;charset=UTF-8" ) )
	        .andDo( print() )
	        .andExpect( jsonPath("$.code").value("GENERIC_ERROR")) ;
	        
        verify(bookService).getBookById(bookId);
	}
	
	
	@Test
	public void testGetBook_notExisting() throws Exception {
		Long bookId = 2L;
		
		// program bookService mock to return null
		when(bookService.getBookById(bookId)).thenReturn(null);
    	
        this.mockMvc.perform( get( "/api/books/" + bookId ).accept( MediaType.parseMediaType( "application/json;charset=UTF-8" ) ) )
	    	.andDo( print() )    
	    	.andExpect( status().isNotFound() );
	        
        verify(bookService).getBookById(bookId);
        
	}
	
	@Test
	public void testCreateBook() throws Exception {
		
		Book book = buildBook(3L, Currency.EUR, 20.50, Arrays.asList(buildAuthor(1L, toDate("1978-09-25"), null)));
		book.setId(null);
		Book savedBook = buildBook(3L, Currency.EUR, 20.50, Arrays.asList(buildAuthor(1L, toDate("1978-09-25"), null)));
		when(bookService.createBook(book)).thenReturn(savedBook);
		
		this.mockMvc.perform( post( "/api/books" ).accept( MediaType.parseMediaType( "application/json;charset=UTF-8" ) )
        		.content(Jackson2ObjectMapperBuilder.json().build().writeValueAsString(book))
        		.contentType(MediaType.APPLICATION_JSON))
        	.andDo( print() )
	        .andExpect( status().isCreated() )
	        .andExpect( content().contentType( "application/json;charset=UTF-8" ) )
	        .andExpect( jsonPath( "$.id" ).value( savedBook.getId().intValue() ) )
	        .andExpect( jsonPath( "$.title" ).value( savedBook.getTitle() ) )
	        .andExpect( jsonPath( "$.description" ).value( savedBook.getDescription() ) );
		
		verify(bookService).createBook(book);
	}
	
	
	@Test
	public void testCreateBook_notValid() throws Exception {
		
		Book book = buildBook(3L, null, 20.50, Arrays.asList(buildAuthor(1L, toDate("1978-09-25"), null)));
		book.setId(null);
		// set mandatory field to null
		book.setTitle(null);
		book.setIsbn("ssss");
		
		ImmutableMap.of("field","isbn","rejectedValue","ssss","message","ISBN not valid");
		this.mockMvc.perform( post( "/api/books" ).accept( MediaType.parseMediaType( "application/json;charset=UTF-8" ) )
        		.content(Jackson2ObjectMapperBuilder.json().build().writeValueAsString(book))
        		.contentType(MediaType.APPLICATION_JSON))
        	.andDo( print() )
	        .andExpect( status().isBadRequest() )
	        .andExpect( content().contentType( "application/json;charset=UTF-8" ) )
	        .andExpect( jsonPath( "$.code" ).value( ErrorCode.VALIDATION_ERROR ) )
	        .andExpect( jsonPath( "$.message" ).value( "Validation Failure" ) )
			.andExpect( jsonPath( "$.violations", hasSize(3)) )
			.andExpect( jsonPath( "$.violations[?(@.field == 'isbn' && @.code == 'error.validation.isbn.notvalid' && @.rejectedValue == 'ssss')]").exists())
			.andExpect( jsonPath( "$.violations[?(@.field == 'title' && @.code == 'error.validation.title.notnull')]").exists())
			.andExpect( jsonPath( "$.violations[?(@.field == 'currency' && @.code == 'error.validation.currency.notnull')]").exists());
	}
	
	@Test
	public void testCreateBook_InternalServerError() throws Exception {
		
		Book book = buildBook(3L, Currency.EUR, 20.50, Arrays.asList(buildAuthor(1L, toDate("1978-09-25"), null)));
		book.setId(null);

		when(bookService.createBook(book)).thenThrow(new BookServiceException());
		
		this.mockMvc.perform( post( "/api/books" ).accept( MediaType.parseMediaType( "application/json;charset=UTF-8" ) )
    		.content(Jackson2ObjectMapperBuilder.json().build().writeValueAsString(book))
    		.contentType(MediaType.APPLICATION_JSON))
            .andDo( print() )
	        .andExpect( jsonPath("$.code").value("GENERIC_ERROR")) ;

        verify(bookService).createBook(book);
	}
	
	/*
	 * UTILITY METHODS
	 */
	private Author buildAuthor(Long id, LocalDate born, LocalDate died) {
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
				.withIsbn("88-04-50279-7")
				.withPrice(price)
				.withAuthors(authors)
				.build();
		return book;
	}
	
	private LocalDate toDate(String date) throws ParseException {
		return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
	}

}
