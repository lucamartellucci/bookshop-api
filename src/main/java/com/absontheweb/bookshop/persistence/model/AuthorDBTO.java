package com.absontheweb.bookshop.persistence.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="author")
public class AuthorDBTO {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false, length=50)
	private String name;
	
	@Column(nullable=false, length=50)
	private String surname;
	
	@Column(name="birth_place", nullable=false, length=50)
	private String birthplace;
	
	@Column(name="born", nullable=false)
	private LocalDate born;
	
	@Column(name="died", nullable=true)
	private LocalDate died;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy="authors")
	private List<BookDBTO> books;


}
