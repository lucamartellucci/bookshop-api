package io.lucci.bookshop.service.impl;

import static io.lucci.bookshop.book.model.adapter.BookAdapter.ADAPT_AUTHORS;
import static io.lucci.bookshop.book.model.adapter.BookAdapter.DO_NOT_ADAPT_AUTHORS;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.lucci.bookshop.book.model.Author;
import io.lucci.bookshop.book.model.Book;
import io.lucci.bookshop.book.model.adapter.AuthorAdapter;
import io.lucci.bookshop.book.model.adapter.BookAdapter;
import io.lucci.bookshop.model.PaginatorResult;
import io.lucci.bookshop.model.SimplePaginator;
import io.lucci.bookshop.persistence.model.AuthorDBTO;
import io.lucci.bookshop.persistence.model.BookDBTO;
import io.lucci.bookshop.persistence.repository.AuthorDBTORepository;
import io.lucci.bookshop.persistence.repository.BookDBTORepository;
import io.lucci.bookshop.service.BookService;
import io.lucci.bookshop.service.exception.BookServiceException;


@Service
@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
public class BookServiceImpl implements BookService {
	
	private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
	
	@Autowired
	private BookDBTORepository bookRepository;
	
	@Autowired
	private AuthorDBTORepository authorRepository;
	
	@Autowired
	private BookAdapter bookAdapter;
	
	@Autowired
	private AuthorAdapter authorAdapter;

	@Override
	public Book getById(Long id) throws BookServiceException {
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
	public Book searchByTitle(String title) throws BookServiceException {
		try {
			logger.info("Loading book with title {}", title);
			return bookAdapter.toBook(bookRepository.findByTitle(title));
		} catch (Exception e) {
			logger.error("Unable to find the Book with title {}", title, e);
			throw new BookServiceException(String.format("Unable to find the Book with title %s",title), e);
		}
	}

	@Override
	public List<Book> getAll() throws BookServiceException {
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
	public Book createNew(Book book) throws BookServiceException {
		try {
			logger.info("Create new book with title [{}]", book.getTitle());
			logger.trace("Create book {}", book);
			
			BookDBTO bookDBTO = bookAdapter.toDBTO(book, DO_NOT_ADAPT_AUTHORS);
			
			if (book.getAuthors().size() > 0){
				logger.debug("Managing [{}] authors", book.getAuthors().size());
				List<AuthorDBTO> authorDBTOList = new ArrayList<AuthorDBTO>();
				for (Author author : book.getAuthors()) {
					if (author.getId() != null) {
						authorDBTOList.add(authorRepository.findOne(author.getId()));
					} else {
						AuthorDBTO authorDBTO = authorAdapter.toDBTO(author, false);
						authorDBTO = authorRepository.save(authorDBTO);
						authorDBTOList.add(authorDBTO);
					}
				}
				bookDBTO.setAuthors(authorDBTOList);
			}
			
			bookDBTO = bookRepository.save(bookDBTO);
			
			return bookAdapter.toBook(bookDBTO, ADAPT_AUTHORS);
		} catch (Exception e) {
			logger.error("Unable to create the book with title [{}]", book.getTitle(), e);
			throw new BookServiceException(String.format("Unable to create the book with title [{%s}]", book.getTitle()), e);
		}
	}

	@Override
	public PaginatorResult<Book> getByPage(SimplePaginator paginator)
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

	public void setBookAdapter(BookAdapter bookAdapter) {
		this.bookAdapter = bookAdapter;
	}

	public void setAuthorAdapter(AuthorAdapter authorAdapter) {
		this.authorAdapter = authorAdapter;
	}

	

}
