package com.absontheweb.bookshop.book.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
@SuppressWarnings("PMD.UnusedPrivateField")
public class Author implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String surname;
	private String birthplace;
	private LocalDate born;
	private LocalDate died;
	private List<Book> books;
	
}
