package com.absontheweb.library.service;

import java.util.List;

import javax.transaction.Transactional;

import com.absontheweb.library.model.Book;
import com.absontheweb.library.service.exception.BookServiceException;

public interface BookService {
	
	@Transactional
	public Book getBookById(final Long id) throws BookServiceException;
	
	@Transactional
	public Book getBookByTitle(final String title) throws BookServiceException;
	
	@Transactional
	public List<Book> getAllBooks() throws BookServiceException;
	
	public Book createBook(final Book book) throws BookServiceException;
	
}
