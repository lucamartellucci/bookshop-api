package com.absontheweb.library.web.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.absontheweb.library.model.Book;
import com.absontheweb.library.service.BookService;
import com.absontheweb.library.web.controller.exception.InternalServerErrorException;

@RestController
@RequestMapping("/api")
public class BookController {

	private static Logger logger = LoggerFactory.getLogger(BookController.class);
	
	
	@Autowired
	private BookService bookService;
	
	@RequestMapping(value = "/books", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Book>> getAllBooks() throws InternalServerErrorException {
		try {
			List<Book> books = bookService.getAllBooks();
			ResponseEntity<List<Book>> response = ResponseEntity.ok(books);
			return response;
		} catch (Exception e) {
			throw new InternalServerErrorException("Unable to load all books", e);
		}
	}
	
	@RequestMapping(value = "/books/{id}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Book> getBook(@PathVariable("id") Long id) throws InternalServerErrorException {
		try {
			ResponseEntity<Book> response = null;
			Book book = bookService.getBookById(id);
			if (book != null) {
				response = ResponseEntity.ok(book);
			} else {
				response = new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
			}
			return response;
		} catch (Exception e) {
			throw new InternalServerErrorException(String.format("Unable retrieve book with id [%d]", id), e);
		}
	}
	
	@RequestMapping(value = "/books", 
			method = RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Book> createBook(@RequestBody Book book) throws InternalServerErrorException {
		try {
			Book savedBook = bookService.createBook(book);
			return ResponseEntity.created(new URI(String.format("/api/book/%d", savedBook.getId()))).body(savedBook);
		} catch (Exception e) {
			throw new InternalServerErrorException(String.format("Unable create book with title [%s]", book.getTitle()), e);
		}
	}

}
