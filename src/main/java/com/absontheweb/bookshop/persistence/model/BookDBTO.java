package com.absontheweb.bookshop.persistence.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.absontheweb.bookshop.book.model.Currency;

@Entity
@Table(name="book")
public class BookDBTO {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String title;

	private String isbn;
	
	private String description;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
		name="book_author", 
		joinColumns = {@JoinColumn(name="book_id")}, 
		inverseJoinColumns = {@JoinColumn(name="author_id")}
	)
	private List<AuthorDBTO> authors;
	
	private Double price;
	
	private Integer year;
	
	@Enumerated(EnumType.STRING)
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

	public List<AuthorDBTO> getAuthors() {
		return authors;
	}

	public void setAuthors(List<AuthorDBTO> authors) {
		this.authors = authors;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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
		builder.append("BookDBTO [id=").append(id).append(", title=")
				.append(title).append(", isbn=").append(isbn)
				.append(", description=").append(description)
				.append(", authors=").append(authors).append(", price=")
				.append(price).append(", year=").append(year)
				.append(", currency=").append(currency).append("]");
		return builder.toString();
	}
	

}
