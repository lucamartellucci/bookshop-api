package com.absontheweb.library.model.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.absontheweb.library.model.Author;
import com.absontheweb.library.model.Book;
import com.absontheweb.library.persistence.model.AuthorDBTO;

@Component
public class AuthorAdapter {
	
	@Autowired
	private BookAdapter bookAdapter;
	
	public Author toAuthor(AuthorDBTO authorDBTO) {
		return toAuthor(authorDBTO,false);
	}
	
	public Author toAuthor(AuthorDBTO authorDBTO, boolean adaptBooks) {
		if (authorDBTO == null) {
			return null;
		}
		Author author = new Author();
		BeanUtils.copyProperties(authorDBTO, author, "books");
		if (adaptBooks) {
			author.setBooks(bookAdapter.toBooks(authorDBTO.getBooks()));
		}
		return author;
	}
	
	public Book toDBTO(Book book) {
		return null;
	}

	public List<Author> toAuthors(List<AuthorDBTO> authors) {
		return toAuthors(authors,false);
	}
	
	public List<Author> toAuthors(List<AuthorDBTO> authorDBTOs, boolean adaptBooks) {
		if (authorDBTOs == null) {
			return null;
		}
		List<Author> authors = new ArrayList<>();
		for (AuthorDBTO authorDBTO : authorDBTOs) {
			authors.add(toAuthor(authorDBTO, adaptBooks));
		}
		return authors;
	}
}
