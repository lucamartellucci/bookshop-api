package com.absontheweb.bookshop.book.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.absontheweb.bookshop.application.Application;
import com.absontheweb.bookshop.application.MessageSourceConfig;
import com.absontheweb.bookshop.application.PersistenceConfig;
import com.absontheweb.bookshop.application.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={Application.class, MessageSourceConfig.class, PersistenceConfig.class, WebConfig.class})
@ActiveProfiles(profiles = { "dbtest" })
@WebAppConfiguration
public class BookControllerDocumentation {
	
	@Rule
	public RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	
	private RestDocumentationResultHandler document;

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
	    	.andExpect(status().isOk()); 
	    
	}

}
