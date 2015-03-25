package com.absontheweb.library.model.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.absontheweb.library.model.Book;
import com.absontheweb.library.persistence.model.BookDBTO;

@Component
public class BookAdapter {
	
	public static final boolean ADAPT_AUTHORS = true;
	public static final boolean DO_NOT_ADAPT_AUTHORS = false;
	public static final boolean ADAPT_BOOKS = true;
	public static final boolean DO_NOT_ADAPT_BOOKS = false;
	
	@Autowired
	private AuthorAdapter authorAdapter;
	
	public List<Book> toBooks(List<BookDBTO> bookDBTOs) {
		return toBooks(bookDBTOs, false);
	}
	
	public List<Book> toBooks(List<BookDBTO> bookDBTOs, boolean adaptAuthors) {
		List<Book> books = new ArrayList<>();
		for (BookDBTO bookDBTO : bookDBTOs) {
			books.add(toBook(bookDBTO,adaptAuthors));
		}
		return books;
	}
	
	public Book toBook(BookDBTO bookDBTO) {
		return toBook(bookDBTO,false);
	}
	
	public Book toBook(BookDBTO bookDBTO, boolean adaptAuthors) {
		if (bookDBTO == null) {
			return null;
		}
		Book book = new Book();
		BeanUtils.copyProperties(bookDBTO, book, "authors");
		if (adaptAuthors) {
			book.authors(authorAdapter.toAuthors(bookDBTO.getAuthors()));
		}
		return book;
	}
	
	public List<BookDBTO> toDBTO(List<Book> books, boolean adaptAuthors) {
		if (books == null) {
			return null;
		}
		List<BookDBTO> bookDBTOs = new ArrayList<>();
		for (Book book : books) {
			bookDBTOs.add(toDBTO(book, adaptAuthors));
		}
		return bookDBTOs;
	} 
	
	public BookDBTO toDBTO(Book book, boolean adaptAuthors) {
		if (book == null) {
			return null;
		}
		BookDBTO bookDBTO = new BookDBTO();
		BeanUtils.copyProperties(book, bookDBTO, "authors");
		if (adaptAuthors) {
			bookDBTO.setAuthors(authorAdapter.toDBTO(book.getAuthors()));
		}
		return bookDBTO;
	}
}
