package io.lucci.bookshop.book.model;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.lucci.bookshop.book.model.validation.Isbn;
import lombok.Data;

@Data
public class Book {
	
	private Long id;
	
	@NotNull(message="{error.validation.title.notnull}")
	@Size(max=150, message="{error.validation.title.maxlength}")
	private String title;
	
	@Isbn
	private String isbn;
	
	@Size(max=3000, message="{error.validation.description.maxlength}")
	private String description;
	
	@NotNull(message="{error.validation.author.notnull}")
	@Size(min=1, message="{error.validation.author.atleastone.required}")
	private List<Author> authors;
	
	@NotNull(message="{error.validation.price.notnull}")
	private Double price;
	
	private LocalDate releaseDate;
	
	@NotNull(message="{error.validation.currency.notnull}")
	private Currency currency;
	
	
}
