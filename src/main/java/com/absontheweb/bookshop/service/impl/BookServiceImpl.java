package com.absontheweb.bookshop.service.impl;

import static com.absontheweb.bookshop.book.model.adapter.BookAdapter.ADAPT_AUTHORS;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.absontheweb.bookshop.book.model.Book;
import com.absontheweb.bookshop.book.model.adapter.BookAdapter;
import com.absontheweb.bookshop.model.PaginatorResult;
import com.absontheweb.bookshop.model.SimplePaginator;
import com.absontheweb.bookshop.persistence.model.BookDBTO;
import com.absontheweb.bookshop.persistence.repository.BookDBTORepository;
import com.absontheweb.bookshop.service.BookService;
import com.absontheweb.bookshop.service.exception.BookServiceException;

@Service
@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
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
			Book book = bookAdapter.toBook(bookRepository.findOne(id), ADAPT_AUTHORS);
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
	@Transactional(readOnly=false)
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

	@Override
	public PaginatorResult<Book> getBooks(SimplePaginator paginator)
			throws BookServiceException {

		try {
			if (paginator == null) {
				logger.info("Load all books");
				List<Book> books = bookAdapter.toBooks(bookRepository.findAll(), ADAPT_AUTHORS);
				PaginatorResult<Book> paginatedBook = new PaginatorResult<Book>();
				paginatedBook.setCurrentPage(0);
				paginatedBook.setPageSize(books.size());
				paginatedBook.setResult(books);
				paginatedBook.setTotalItems(books.size());
				paginatedBook.setTotalPages(1);
				return paginatedBook;
			} else {
				Page<BookDBTO> books = bookRepository.findAll(new PageRequest(paginator.getPage(), paginator.getSize()));
				PaginatorResult<Book> paginatedBook = new PaginatorResult<Book>();
				paginatedBook.setCurrentPage(paginator.getPage());
				paginatedBook.setPageSize(books.getSize());
				paginatedBook.setResult(bookAdapter.toBooks(books.getContent(), ADAPT_AUTHORS));
				paginatedBook.setTotalItems(books.getTotalElements());
				paginatedBook.setTotalPages(books.getTotalPages());
				return paginatedBook;
			}
			
		} catch (Exception e) {
			logger.error("Unable to load books", e);
			throw new BookServiceException("Unable to load books", e);
		}
	}

}
