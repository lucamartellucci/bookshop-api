package com.absontheweb.bookshop.book.model.validation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.absontheweb.bookshop.book.model.Author;
import com.absontheweb.bookshop.book.model.AuthorBuilder;
import com.absontheweb.bookshop.book.model.Book;
import com.absontheweb.bookshop.book.model.BookBuilder;
import com.absontheweb.bookshop.book.model.Currency;


public class IsbnValidatorTest {

	private static final Logger logger = LoggerFactory.getLogger(IsbnValidatorTest.class);
	
	
	private static final String VALID_ISBN = "8868950715";
	private static final String NOT_VALID_ISBN = "XXX";
	
	private Book book;
	private static Validator validator;
	
	@BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
	
	@Before
    public void before() {
		Author author = AuthorBuilder.author().build();
		book=BookBuilder.book()
			.withTitle("title")
			.withCurrency(Currency.EUR)
			.withDescription("description")
			.withPrice(12.0)
			.withAddedAuthor(author)
			.build();
    }
 
    @Test
    public void testIsValid() {
        book.setIsbn(VALID_ISBN);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations.size(),is(0));
    }
	
	@Test
	public void testIsNotValid() throws Exception {
		book.setIsbn(NOT_VALID_ISBN);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        logger.debug("Violations are {}", violations);
        assertThat(violations.size(),is(1));
        Iterator<ConstraintViolation<Book>> iterator = violations.iterator();
        ConstraintViolation<Book> constraintViolation = iterator.next();
        assertThat(constraintViolation.getMessage(),is("{error.validation.isbn.notvalid}"));
	}

}
