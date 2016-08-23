package io.lucci.bookshop.controller.exception;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;



public class RestResponseEntityExceptionHandlerTest {
	
	private RestResponseEntityExceptionHandler handler;

	@Before
	public void setUp() {
		this.handler = new RestResponseEntityExceptionHandler();
	}

	@Test
	public void testExtractCode() throws Exception {
		assertThat(handler.extractCode("{message}"),is("message"));
		assertThat(handler.extractCode("message"),is("message"));
		assertThat(handler.extractCode(null),is(nullValue()));
	}
	
	

}
