// CHECKSTYLE:OFF
/**
 * Source code generated by Fluent Builders Generator
 * Do not modify this file
 * See generator home page at: http://code.google.com/p/fluent-builders-generator-eclipse-plugin/
 */

package com.absontheweb.bookshop.book.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookBuilder extends BookBuilderBase<BookBuilder> {
	public static BookBuilder book() {
		return new BookBuilder();
	}

	public BookBuilder() {
		super(new Book());
	}

	public Book build() {
		return getInstance();
	}
}

class BookBuilderBase<T extends BookBuilderBase<T>> {
	private Book instance;

	protected BookBuilderBase(Book aInstance) {
		instance = aInstance;
	}

	protected Book getInstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	public T withId(Long aValue) {
		instance.setId(aValue);

		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withTitle(String aValue) {
		instance.setTitle(aValue);

		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withIsbn(String aValue) {
		instance.setIsbn(aValue);

		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withDescription(String aValue) {
		instance.setDescription(aValue);

		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withAuthors(List<Author> aValue) {
		instance.setAuthors(aValue);

		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withAddedAuthor(Author aValue) {
		if (instance.getAuthors() == null) {
			instance.setAuthors(new ArrayList<Author>());
		}

		((ArrayList<Author>) instance.getAuthors()).add(aValue);

		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withPrice(Double aValue) {
		instance.setPrice(aValue);

		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withReleaseDate(LocalDate aValue) {
		instance.setReleaseDate(aValue);

		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withCurrency(Currency aValue) {
		instance.setCurrency(aValue);

		return (T) this;
	}
}
