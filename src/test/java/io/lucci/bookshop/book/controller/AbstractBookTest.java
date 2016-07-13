package io.lucci.bookshop.book.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.lucci.bookshop.book.model.Author;
import io.lucci.bookshop.book.model.AuthorBuilder;
import io.lucci.bookshop.book.model.Book;
import io.lucci.bookshop.book.model.BookBuilder;
import io.lucci.bookshop.book.model.Currency;
import io.lucci.bookshop.util.ISBNGenerator;

public class AbstractBookTest {

	/*
	 * UTILITY METHODS
	 */
	protected Author buildAuthor(Long id, LocalDate born, LocalDate died) {
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
	
	protected Book buildBook(Long id, Currency currency, Double price, List<Author> authors) throws Exception {
		Book book = BookBuilder.book()
				.withId(id)
				.withTitle("title"+id)
				.withDescription("desc"+id)
				.withCurrency(currency)
				.withIsbn(ISBNGenerator.generate())
				.withPrice(price)
				.withAuthors(authors)
				.withReleaseDate(toDate("2006-12-31"))
				.build();
		return book;
	}
	
	protected Book buildBrandNewBook() throws Exception {
		List<Author> authors = new ArrayList<>();
		authors.add(buildBrandNewAuthor());
		
		Book book = BookBuilder.book()
				.withTitle("title"+UUID.randomUUID().toString())
				.withDescription("desc"+UUID.randomUUID().toString())
				.withCurrency(Currency.EUR)
				.withIsbn(ISBNGenerator.generate())
				.withPrice(12d)
				.withAuthors(authors)
				.withReleaseDate(toDate("2006-12-31"))
			.build();
		return book;
	}
	
	private Author buildBrandNewAuthor() throws Exception {
		Author author = AuthorBuilder.author()
				.withBirthplace("bithplace"+UUID.randomUUID().toString())
				.withName("name"+UUID.randomUUID().toString())
				.withSurname("surname"+UUID.randomUUID().toString())
				.withBorn(toDate("2006-12-31"))
				.build();
		return author;
	}

	protected LocalDate toDate(String date) throws ParseException {
		return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
	}

}