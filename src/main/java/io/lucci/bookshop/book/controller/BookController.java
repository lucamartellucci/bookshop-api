package io.lucci.bookshop.book.controller;

import static io.lucci.bookshop.security.SecurityRules.*;
import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.lucci.bookshop.book.model.Book;
import io.lucci.bookshop.controller.exception.InternalServerErrorException;
import io.lucci.bookshop.controller.exception.ResourceNotFoundException;
import io.lucci.bookshop.controller.resolver.CurrentUser;
import io.lucci.bookshop.controller.resolver.Paginator;
import io.lucci.bookshop.model.PaginatorResult;
import io.lucci.bookshop.model.SimplePaginator;
import io.lucci.bookshop.model.User;
import io.lucci.bookshop.security.SecurityRules;
import io.lucci.bookshop.service.BookService;
import io.lucci.bookshop.service.exception.BookServiceException;

@RestController
@RequestMapping("/api")
public class BookController {

	private static Logger logger = LoggerFactory.getLogger(BookController.class);
	
	
	@Autowired
	private BookService bookService;
	
	
	@PreAuthorize(BOOKS_GET)
	@RequestMapping(value = "/books", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PaginatorResult<Book>> getBooks(@Paginator SimplePaginator paginator) throws InternalServerErrorException {
		try {
			logger.debug("Paginator is: {}", paginator);
			PaginatorResult<Book> paginatedBooks = bookService.getByPage(paginator);
			ResponseEntity<PaginatorResult<Book>> response = ResponseEntity.ok(paginatedBooks);
			return response;
		} catch (Exception e) {
			throw new InternalServerErrorException("Unable to load all books", e);
		}
	}
	

	@PreAuthorize(BOOKS_GET_DETAIL)
	@RequestMapping(value = "/books/{id}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Book> getBook(@PathVariable("id") Long id, @CurrentUser User user) 
			throws InternalServerErrorException, ResourceNotFoundException {
		try {
			logger.debug("Logger User is: {}", user);
			Book book = bookService.getById(id);
			if (book != null) {
				return ResponseEntity.ok(book);
			} 
		} catch (BookServiceException e) {
			throw new InternalServerErrorException(String.format("Unable retrieve book with id [%d]", id), e);
		}
		
		throw new ResourceNotFoundException(String.format("Book with id [%d] does not exist", id));
	}
	

	@PreAuthorize(BOOKS_ADD)
	@RequestMapping(value = "/books", 
			method = RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) throws InternalServerErrorException {
		try {
			Book savedBook = bookService.createNew(book);
			return ResponseEntity.created(new URI(String.format("/api/book/%d", savedBook.getId()))).body(savedBook);
		} catch (Exception e) {
			throw new InternalServerErrorException(String.format("Unable create book with title [%s]", book.getTitle()), e);
		}
	}

}
