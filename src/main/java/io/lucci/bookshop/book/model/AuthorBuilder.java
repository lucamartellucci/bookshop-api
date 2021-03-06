// CHECKSTYLE:OFF
/**
 * Source code generated by Fluent Builders Generator
 * Do not modify this file
 * See generator home page at: http://code.google.com/p/fluent-builders-generator-eclipse-plugin/
 */

package io.lucci.bookshop.book.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AuthorBuilder extends AuthorBuilderBase<AuthorBuilder> {
	public static AuthorBuilder author() {
		return new AuthorBuilder();
	}

	public AuthorBuilder() {
		super(new Author());
	}

	public Author build() {
		return getInstance();
	}
}

class AuthorBuilderBase<T extends AuthorBuilderBase<T>> {
	private Author instance;

	protected AuthorBuilderBase(Author aInstance) {
		instance = aInstance;
	}

	protected Author getInstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	public T withId(Long aValue) {
		instance.setId(aValue);

		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withName(String aValue) {
		instance.setName(aValue);

		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withSurname(String aValue) {
		instance.setSurname(aValue);

		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withBirthplace(String aValue) {
		instance.setBirthplace(aValue);

		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withBorn(LocalDate aValue) {
		instance.setBorn(aValue);

		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withDied(LocalDate aValue) {
		instance.setDied(aValue);

		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withBooks(List<Book> aValue) {
		instance.setBooks(aValue);

		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withAddedBook(Book aValue) {
		if (instance.getBooks() == null) {
			instance.setBooks(new ArrayList<Book>());
		}

		((ArrayList<Book>) instance.getBooks()).add(aValue);

		return (T) this;
	}

	public AddedBookBookBuilder withAddedBook() {
		Book obj = new Book();

		withAddedBook(obj);

		return new AddedBookBookBuilder(obj);
	}

	public class AddedBookBookBuilder extends
			BookBuilderBase<AddedBookBookBuilder> {
		public AddedBookBookBuilder(Book aInstance) {
			super(aInstance);
		}

		@SuppressWarnings("unchecked")
		public T endBook() {
			return (T) AuthorBuilderBase.this;
		}
	}

	public static class BookBuilderBase<T extends BookBuilderBase<T>> {
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
}
