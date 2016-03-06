package com.absontheweb.bookshop.i18n.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.absontheweb.bookshop.i18n.model.Language;
import com.absontheweb.bookshop.i18n.model.MessageResourceLocale;
import com.absontheweb.bookshop.service.I18nService;
import com.absontheweb.bookshop.service.exception.I18nServiceException;
import com.absontheweb.bookshop.test.I18nControllerTestConfig;

@RunWith ( SpringJUnit4ClassRunner.class )
@ContextConfiguration ( classes = { I18nControllerTestConfig.class } )
@WebAppConfiguration
public class I18nControllerTest {
	

    @Autowired
    private WebApplicationContext wac;
    
    @Autowired
    private I18nService i18nService;

    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup( this.wac ).build();
        reset(i18nService);
    }


	@Test
	public void testGetLanguages() throws Exception {
		
		List<Language> supportedLanguages = Arrays.asList(
				new Language("italian","IT","it_flag"), 
				new Language("english","EN","en_flag"));
		
		when(i18nService.retrieveSupportedLanguages()).thenReturn(supportedLanguages);
		    	
        this.mockMvc.perform( get( "/api/i18n/languages" ).accept( MediaType.parseMediaType( "application/json;charset=UTF-8" ) ) )
	        .andExpect( status().isOk() )
	        .andExpect( content().contentType( "application/json;charset=UTF-8" ) )
	        .andDo( print() )
	        .andExpect( jsonPath( "$", hasSize(2)) )
	        .andExpect( jsonPath( "$[0].name" ).value( "italian" ) )
	        .andExpect( jsonPath( "$[0].locale" ).value( "IT" ) )
	        .andExpect( jsonPath( "$[0].flagUrl" ).value( "it_flag" ) )
	        .andExpect( jsonPath( "$[1].name" ).value( "english" ) )
	        .andExpect( jsonPath( "$[1].locale" ).value( "EN" ) )
	        .andExpect( jsonPath( "$[1].flagUrl" ).value( "en_flag" ) );
			        
        verify(i18nService).retrieveSupportedLanguages();
	}
	
	@Test
	public void testGetLanguages_internalError() throws Exception {
		final String message = "Internal error";
		when(i18nService.retrieveSupportedLanguages()).thenThrow(new I18nServiceException(message));
		this.mockMvc.perform( get( "/api/i18n/languages" ).accept( MediaType.parseMediaType( "application/json;charset=UTF-8" ) ) )
			.andExpect( status().is5xxServerError() )
	        .andExpect( content().contentType( "application/json;charset=UTF-8" ) )
	        .andDo( print() )
	        .andExpect( jsonPath( "$.code" ).value( "GENERIC_ERROR" ) )
	        .andExpect( jsonPath( "$.message" ).value( message ) );
	}


	@Test
	public void testGetMessages() throws Exception {
		
		MessageResourceLocale mrl = new MessageResourceLocale();
		Properties messages = new Properties();
		messages.setProperty("key1", "value1");
		messages.setProperty("key2", "value2");
		mrl.getPropertiesMap().put("IT", messages);
		
		when(i18nService.retrieveMessageResourceLocale()).thenReturn(mrl);

		this.mockMvc.perform( get( "/api/i18n/messages/IT" ).accept( MediaType.parseMediaType( "application/json;charset=UTF-8" ) ) )
	        .andExpect( status().isOk() )
	        .andExpect( content().contentType( "application/json;charset=UTF-8" ) )
	        .andDo( print() )
	        .andExpect( jsonPath( "$.key1" ).value( "value1" ) )
	        .andExpect( jsonPath( "$.key2" ).value( "value2" ) );
		
		verify(i18nService).retrieveMessageResourceLocale();
	}
	
	@Test
	public void testGetMessages_internalError() throws Exception {
		final String message = "Internal error";
		when(i18nService.retrieveMessageResourceLocale()).thenThrow(new I18nServiceException(message));
		this.mockMvc.perform( get( "/api/i18n/messages/EN" ).accept( MediaType.parseMediaType( "application/json;charset=UTF-8" ) ) )
			.andExpect( status().is5xxServerError() )
	        .andExpect( content().contentType( "application/json;charset=UTF-8" ) )
	        .andDo( print() )
	        .andExpect( jsonPath( "$.code" ).value( "GENERIC_ERROR" ) )
	        .andExpect( jsonPath( "$.message" ).value( message ) );
	}

}
