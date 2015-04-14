package com.absontheweb.bookshop.service;

import java.util.List;

import javax.transaction.Transactional;

import com.absontheweb.bookshop.model.Book;
import com.absontheweb.bookshop.model.PaginatorResult;
import com.absontheweb.bookshop.model.SimplePaginator;
import com.absontheweb.bookshop.service.exception.BookServiceException;

@Transactional
public interface BookService {
	
	public Book getBookById(final Long id) throws BookServiceException;
	
	public Book getBookByTitle(final String title) throws BookServiceException;
	
	public List<Book> getAllBooks() throws BookServiceException;
	
	public Book createBook(final Book book) throws BookServiceException;

	public PaginatorResult<Book> getBooks(SimplePaginator paginator)  throws BookServiceException;

}
