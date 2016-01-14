package com.absontheweb.bookshop.persistence.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
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
import com.absontheweb.bookshop.model.StorageProvider;

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
	
	@Column(name="release_date")
	private LocalDate releaseDate;
	
	@Enumerated(EnumType.STRING)
	private Currency currency;
	
	@Column(name="cover_name")
	private String coverName;

	@Enumerated(EnumType.STRING)
	@Column(name="cover_location")
	private StorageProvider coverLocation;

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

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	public String getCoverName() {
		return coverName;
	}

	public void setCoverName(String coverName) {
		this.coverName = coverName;
	}

	public StorageProvider getCoverLocation() {
		return coverLocation;
	}

	public void setCoverLocation(StorageProvider coverLocation) {
		this.coverLocation = coverLocation;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BookDBTO [id=");
		builder.append(id);
		builder.append(", title=");
		builder.append(title);
		builder.append(", isbn=");
		builder.append(isbn);
		builder.append(", description=");
		builder.append(description);
		builder.append(", authors=");
		builder.append(authors);
		builder.append(", price=");
		builder.append(price);
		builder.append(", releaseDate=");
		builder.append(releaseDate);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", coverName=");
		builder.append(coverName);
		builder.append(", coverLocation=");
		builder.append(coverLocation);
		builder.append("]");
		return builder.toString();
	}

}
