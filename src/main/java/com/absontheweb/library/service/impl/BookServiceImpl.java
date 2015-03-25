package com.absontheweb.library.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.absontheweb.library.model.Book;
import com.absontheweb.library.model.adapter.BookAdapter;
import com.absontheweb.library.persistence.model.BookDBTO;
import com.absontheweb.library.persistence.repository.BookDBTORepository;
import com.absontheweb.library.service.BookService;
import com.absontheweb.library.service.exception.BookServiceException;
import static com.absontheweb.library.model.adapter.BookAdapter.ADAPT_AUTHORS;

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
			logger.info("Loading book with id {}", id);
			Book book = bookAdapter.toBook(bookRepository.getOne(id), ADAPT_AUTHORS);
			logger.info(book != null ? "Book found" : "Book not found");
			return book;
		} catch (Exception e) {
			logger.error("Unable to find the Book with id {}", id, e);
			throw new BookServiceException(String.format("Unable to find the Book with id %d",id), e);
		}
	}

	@Override
	public Book getBookByTitle(String title) throws BookServiceException {
		try {
			logger.info("Loading book with title {}", title);
			return bookAdapter.toBook(bookRepository.findByTitle(title));
		} catch (Exception e) {
			logger.error("Unable to find the Book with title {}", title, e);
			throw new BookServiceException(String.format("Unable to find the Book with title %s",title), e);
		}
	}

	@Override
	public List<Book> getAllBooks() throws BookServiceException {
		try {
			logger.info("Load all books");
			return bookAdapter.toBooks(bookRepository.findAll(), ADAPT_AUTHORS);
		} catch (Exception e) {
			logger.error("Unable to load all books", e);
			throw new BookServiceException("Unable to load all books", e);
		}
	}

	@Override
	public Book createBook(Book book) throws BookServiceException {
		try {
			logger.info("Create new book with title [{}]", book.getTitle());
			logger.trace("Create book {}", book);
			
			BookDBTO bookDBTO = bookRepository.save(bookAdapter.toDBTO(book, ADAPT_AUTHORS));
			return bookAdapter.toBook(bookDBTO, ADAPT_AUTHORS);
		} catch (Exception e) {
			logger.error("Unable to create the book with title [{}]", book.getTitle(), e);
			throw new BookServiceException(String.format("Unable to create the book with title [{%s}]", book.getTitle()), e);
		}
	}

}
