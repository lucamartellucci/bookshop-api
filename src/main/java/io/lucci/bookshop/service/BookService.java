package io.lucci.bookshop.service;

import java.util.List;

import io.lucci.bookshop.book.model.Book;
import io.lucci.bookshop.model.PaginatorResult;
import io.lucci.bookshop.model.SimplePaginator;
import io.lucci.bookshop.service.exception.BookServiceException;

public interface BookService {
	
	public Book getById(final Long id) throws BookServiceException;
	
	public Book searchByTitle(final String title) throws BookServiceException;
	
	public List<Book> getAll() throws BookServiceException;
	
	public Book createNew(final Book book) throws BookServiceException;

	public PaginatorResult<Book> getByPage(SimplePaginator paginator)  throws BookServiceException;

}
