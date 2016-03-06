package com.absontheweb.bookshop.book.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.absontheweb.bookshop.book.model.Author;
import com.absontheweb.bookshop.book.model.AuthorBuilder;
import com.absontheweb.bookshop.book.model.Book;
import com.absontheweb.bookshop.book.model.BookBuilder;
import com.absontheweb.bookshop.book.model.Currency;

public class AbstractBookTest {

	/*
	 * UTILITY METHODS
	 */
	Author buildAuthor(Long id, LocalDate born, LocalDate died) {
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
	
	Book buildBook(Long id, Currency currency, Double price, List<Author> authors) throws Exception {
		Book book = BookBuilder.book()
				.withId(id)
				.withTitle("title"+id)
				.withDescription("desc"+id)
				.withCurrency(currency)
				.withIsbn("88-04-50279-7")
				.withPrice(price)
				.withAuthors(authors)
				.withReleaseDate(toDate("2006-12-31"))
				.build();
		return book;
	}
	
	LocalDate toDate(String date) throws ParseException {
		return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
	}

}