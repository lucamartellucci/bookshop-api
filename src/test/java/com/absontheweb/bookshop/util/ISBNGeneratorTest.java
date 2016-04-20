package com.absontheweb.bookshop.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import org.apache.commons.validator.routines.ISBNValidator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ISBNGeneratorTest {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ISBNGeneratorTest.class);

	@Test
	public void testGenerate() throws Exception {
		
		String isbn = ISBNGenerator.generate();
		assertThat(isbn,is(notNullValue()));
		LOGGER.debug("Generated ISBN is {}", isbn);
		assertTrue(ISBNValidator.getInstance().isValid(isbn));
		isbn = ISBNGenerator.generate();
		assertThat(isbn,is(notNullValue()));
		LOGGER.debug("Generated ISBN is {}", isbn);
		assertTrue(ISBNValidator.getInstance().isValid(isbn));
		isbn = ISBNGenerator.generate();
		assertThat(isbn,is(notNullValue()));
		LOGGER.debug("Generated ISBN is {}", isbn);
		assertTrue(ISBNValidator.getInstance().isValid(isbn));
		isbn = ISBNGenerator.generate();
		assertThat(isbn,is(notNullValue()));
		LOGGER.debug("Generated ISBN is {}", isbn);
		assertTrue(ISBNValidator.getInstance().isValid(isbn));
	}



}
