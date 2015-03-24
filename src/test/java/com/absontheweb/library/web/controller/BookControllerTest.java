package com.absontheweb.library.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.absontheweb.library.model.Author;
import com.absontheweb.library.model.AuthorBuilder;
import com.absontheweb.library.model.Book;
import com.absontheweb.library.model.BookBuilder;
import com.absontheweb.library.model.Currency;
import com.absontheweb.library.service.BookService;

@RunWith ( SpringJUnit4ClassRunner.class )
@ContextConfiguration ( classes = { ControllerTestConfig.class } )
@WebAppConfiguration
public class BookControllerTest {
	

	private static Logger logger = LoggerFactory.getLogger(BookControllerTest.class);

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
	public void testGetAllBooks() throws Exception {
		
		// prepare objects returned by the bookservice
		List<Author> authors = Arrays.asList(buildAuthor(1L, new SimpleDateFormat("dd/MM/yyyy").parse("25/09/1978"), null));
		Book book1 = buildBook(1L, Currency.EUR, 10.0, authors);
		Book book2 = buildBook(2L, Currency.USD, 12.0, authors);
		List<Book> books = Arrays.asList(book1, book2);
		
		// program bookService mock to return a collection of books
		when(bookService.getAllBooks()).thenReturn(books);
    	
        this.mockMvc.perform( get( "/api/books" ).accept( MediaType.parseMediaType( "application/json;charset=UTF-8" ) ) )
	        .andExpect( status().isOk() )
	        .andExpect( content().contentType( "application/json;charset=UTF-8" ) )
	        .andDo( print() )
	        .andExpect( jsonPath( "$", hasSize(2)) )
	        .andExpect( jsonPath( "$[0].id" ).value( book1.getId().intValue() ) )
	        .andExpect( jsonPath( "$[0].title" ).value( book1.getTitle() ) )
	        .andExpect( jsonPath( "$[0].description" ).value( book1.getDescription() ) )
	        .andExpect( jsonPath( "$[0].currency" ).value( book1.getCurrency().toString() ) )
	        .andExpect( jsonPath( "$[0].isbn" ).value( book1.getIsbn() ) )
	        .andExpect( jsonPath( "$[0].price" ).value( book1.getPrice() ) )
	        .andExpect( jsonPath( "$[0].authors", hasSize(1) ))
	        .andExpect( jsonPath( "$[0].authors[0].name" ).value( book1.getAuthors().get(0).getName() ))
	        .andExpect( jsonPath( "$[0].authors[0].surname" ).value( book1.getAuthors().get(0).getSurname() ))
	        .andExpect( jsonPath( "$[0].authors[0].born" ).value( book1.getAuthors().get(0).getBorn().getTime() ))
	        .andExpect( jsonPath( "$[0].authors[0].birthplace" ).value( book1.getAuthors().get(0).getBirthplace() ));
	        
        verify(bookService).getAllBooks();
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
