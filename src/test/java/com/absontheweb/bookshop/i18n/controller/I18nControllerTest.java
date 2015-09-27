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
import com.absontheweb.bookshop.service.I18nService;

import config.I18nControllerTestConfig;

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

}
