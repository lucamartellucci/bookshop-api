package com.absontheweb.bookshop.service;

import java.util.List;

import com.absontheweb.bookshop.book.model.Book;
import com.absontheweb.bookshop.model.PaginatorResult;
import com.absontheweb.bookshop.model.SimplePaginator;
import com.absontheweb.bookshop.service.exception.BookServiceException;

public interface BookService {
	
	public Book getById(final Long id) throws BookServiceException;
	
	public Book searchByTitle(final String title) throws BookServiceException;
	
	public List<Book> getAll() throws BookServiceException;
	
	public Book createNew(final Book book) throws BookServiceException;

	public PaginatorResult<Book> getByPage(SimplePaginator paginator)  throws BookServiceException;

}
