package com.absontheweb.library.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.absontheweb.library.model.Book;
import com.absontheweb.library.service.BookService;
import com.absontheweb.library.web.controller.exception.InternalServerErrorException;

@RestController
@RequestMapping("/api")
public class BookController {

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

}
