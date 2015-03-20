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
			book.authors(authorAdapter.toAuthors(bookDBTO.getAuthors(), false));
		}
		return book;
	}
	
	public Book toDBTO(Book book) {
		return null;
	}
}
