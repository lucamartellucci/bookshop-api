package com.absontheweb.library.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.absontheweb.library.model.Book;
import com.absontheweb.library.model.adapter.BookAdapter;
import com.absontheweb.library.persistence.repository.BookDBTORepository;
import com.absontheweb.library.service.BookService;
import com.absontheweb.library.service.exception.BookServiceException;

@Service
public class BookServiceImpl implements BookService {
	
	private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
	
	@Autowired
	BookDBTORepository bookRepository;
	
	@Autowired
	BookAdapter bookAdapter;

	@Override
	public Book getBookById(Long id) throws BookServiceException {
		try {
			return bookAdapter.toBook(bookRepository.getOne(id));
		} catch (Exception e) {
			logger.error("Unable to find the Book with id {}", id);
			throw new BookServiceException(String.format("Unable to find the Book with id %d",id), e);
		}
	}

	@Override
	public Book getBookByTitle(String title) throws BookServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getAllBooks() throws BookServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
