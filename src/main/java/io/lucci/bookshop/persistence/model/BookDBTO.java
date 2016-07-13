package io.lucci.bookshop.persistence.model;

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

import io.lucci.bookshop.book.model.Currency;
import io.lucci.bookshop.model.StorageProvider;
import lombok.Data;

@Data
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
	
	@Column(name="cover_file_name")
	private String coverFileName;

	@Enumerated(EnumType.STRING)
	@Column(name="cover_file_location")
	private StorageProvider coverFileLocation;

}
