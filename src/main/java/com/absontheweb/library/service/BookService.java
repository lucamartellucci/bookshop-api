package com.absontheweb.library.service;

import java.util.List;

import javax.transaction.Transactional;

import com.absontheweb.library.model.Book;
import com.absontheweb.library.model.PaginatorResult;
import com.absontheweb.library.model.SimplePaginator;
import com.absontheweb.library.service.exception.BookServiceException;

@Transactional
public interface BookService {
	
	public Book getBookById(final Long id) throws BookServiceException;
	
	public Book getBookByTitle(final String title) throws BookServiceException;
	
	public List<Book> getAllBooks() throws BookServiceException;
	
	public Book createBook(final Book book) throws BookServiceException;

	public PaginatorResult<Book> getBooks(SimplePaginator paginator)  throws BookServiceException;

}
