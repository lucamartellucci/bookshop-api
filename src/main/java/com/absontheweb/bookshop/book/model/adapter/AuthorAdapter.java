package com.absontheweb.bookshop.book.model.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.absontheweb.bookshop.book.model.Author;
import com.absontheweb.bookshop.persistence.model.AuthorDBTO;

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
	
	public List<Author> toAuthors(List<AuthorDBTO> authors) {
		return toAuthors(authors,false);
	}
	
	
	public List<Author> toAuthors(List<AuthorDBTO> authorDBTOs, boolean adaptBooks) {
		if (authorDBTOs == null) {
			return null;
		}
		List<Author> authors = new ArrayList<Author>();
		for (AuthorDBTO authorDBTO : authorDBTOs) {
			authors.add(toAuthor(authorDBTO, adaptBooks));
		}
		return authors;
	}
	
	public List<AuthorDBTO> toDBTO(List<Author> authors) {
		return toDBTO(authors, false);
	}

	public List<AuthorDBTO> toDBTO(List<Author> authors, boolean adaptBooks) {
		if (authors == null) {
			return null;
		}
		List<AuthorDBTO> authorDBTOs = new ArrayList<AuthorDBTO>();
		for (Author author : authors) {
			authorDBTOs.add(toDBTO(author, adaptBooks));
		}
		return authorDBTOs;
	}

	private AuthorDBTO toDBTO(Author author, boolean adaptBooks) {
		if (author == null) {
			return null;
		}
		AuthorDBTO authorDBTO = new AuthorDBTO();
		BeanUtils.copyProperties(author, authorDBTO, "books");
		if (adaptBooks) {
			authorDBTO.setBooks(bookAdapter.toDBTO(author.getBooks(), adaptBooks));
		}
		return authorDBTO;
	}
}
