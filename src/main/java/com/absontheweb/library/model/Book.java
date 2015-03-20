package com.absontheweb.library.model;

import java.util.List;

public class Book {
	
	private Long id;
	private String title;
	private String isbn;
	private String description;
	private List<Author> authors;
	private Double price;
	private Integer year;
	private Currency currency;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Author> getAuthors() {
		return authors;
	}
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Book id(Long id) {
		this.id = id;
		return this;
	}
	public Book title(String value) {
		this.title = value;
		return this;
	}
	public Book isbn(String value) {
		this.isbn = value;
		return this;
	}
	public Book description(String value) {
		this.description = value;
		return this;
	}
	public Book authors(List<Author> value) {
		this.authors = value;
		return this;
	}
	public Book price(Double value) {
		this.price = value;
		return this;
	}
	public Book year(Integer value) {
		this.year = value;
		return this;
	}
	
	public Book currency(Currency value) {
		this.currency = value;
		return this;
	}
	
	
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Book [id=").append(id).append(", title=").append(title)
				.append(", isbn=").append(isbn).append(", description=")
				.append(description).append(", authors=").append(authors)
				.append(", price=").append(price).append("]");
		return builder.toString();
	}
	
	

}
