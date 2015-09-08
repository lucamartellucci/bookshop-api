package com.absontheweb.bookshop.controller.exception;

import org.junit.Before;
import org.junit.Test;

import com.absontheweb.bookshop.controller.exception.RestResponseEntityExceptionHandler;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;



public class RestResponseEntityExceptionHandlerTest {
	
	private RestResponseEntityExceptionHandler handler;

	@Before
	public void setup() {
		this.handler = new RestResponseEntityExceptionHandler();
	}

	@Test
	public void testExtractCode() throws Exception {
		assertThat(handler.extractCode("{message}"),is("message"));
		assertThat(handler.extractCode("message"),is("message"));
		assertThat(handler.extractCode(null),is(nullValue()));
	}
	
	

}
