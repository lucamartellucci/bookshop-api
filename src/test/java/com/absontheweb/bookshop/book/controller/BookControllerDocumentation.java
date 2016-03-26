package com.absontheweb.bookshop.book.controller;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.absontheweb.bookshop.application.MessageSourceConfig;
import com.absontheweb.bookshop.book.model.Author;
import com.absontheweb.bookshop.book.model.Book;
import com.absontheweb.bookshop.book.model.Currency;
import com.absontheweb.bookshop.service.BookService;
import com.absontheweb.bookshop.test.base.BookControllerTestConfig;


@RunWith ( SpringJUnit4ClassRunner.class )
@ContextConfiguration ( classes = { BookControllerTestConfig.class, MessageSourceConfig.class }, 
	initializers = ConfigFileApplicationContextInitializer.class )
@WebAppConfiguration
public class BookControllerDocumentation extends AbstractBookTest {
	
	@Rule
	public RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	
	private RestDocumentationResultHandler document;
	
    @Autowired
    private BookService bookService;

	@Before
    public void setUp() {
        this.document = document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(this.document)
                .build();
    }
	
	@Test
	public void getBook() throws Exception {
		
		// prepare objects returned by the bookservice
		List<Author> authors = Arrays.asList(buildAuthor(1L, toDate("1978-09-25"), null));
		Long bookId = 1L;
		Book book = buildBook(bookId, Currency.EUR, 10.0, authors);
		
		// program bookService mock to return the book with id bookId
		when(bookService.getBookById(bookId)).thenReturn(book);
		
		this.document.snippets(responseFields( 
	    		fieldWithPath("id").description("The book's id").type(JsonFieldType.NUMBER), 
	            fieldWithPath("title").description("The book's title").type(JsonFieldType.STRING),
	    		fieldWithPath("isbn").description("The book's ISBN code").type(JsonFieldType.STRING),
	    		fieldWithPath("description").description("The book's short description").type(JsonFieldType.STRING),
	    		fieldWithPath("price").description("The book's price").type(JsonFieldType.NUMBER),
	    		fieldWithPath("releaseDate").description("The book's release date").type(JsonFieldType.NUMBER),
	    		fieldWithPath("currency").description("The book's currency").type(JsonFieldType.STRING),
	    		fieldWithPath("authors").description("The book's author list").type(JsonFieldType.OBJECT),
	    		fieldWithPath("authors[].id").description("The author's id").type(JsonFieldType.NUMBER),
	    		fieldWithPath("authors[].name").description("The author's first name").type(JsonFieldType.STRING),
	    		fieldWithPath("authors[].surname").description("The author's last name").type(JsonFieldType.STRING),
	    		fieldWithPath("authors[].birthplace").description("The author's birthplace").type(JsonFieldType.STRING),
	    		fieldWithPath("authors[].born").description("The author's ISBN birthdate").type(JsonFieldType.NUMBER)
	    ));
		
		this.mockMvc.perform(get("/api/books/1")
			.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
	    	.andExpect(status().isOk()); 
	    
	}
	

}
