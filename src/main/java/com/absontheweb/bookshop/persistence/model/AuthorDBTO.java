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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthPlace) {
		this.birthplace = birthPlace;
	}

	public LocalDate getBorn() {
		return born;
	}

	public void setBorn(LocalDate born) {
		this.born = born;
	}

	public LocalDate getDied() {
		return died;
	}

	public void setDied(LocalDate died) {
		this.died = died;
	}
	public List<BookDBTO> getBooks() {
		return books;
	}

	public void setBooks(List<BookDBTO> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AuthorDBTO [id=").append(id).append(", name=")
				.append(name).append(", surname=").append(surname)
				.append(", birthplace=").append(birthplace).append(", born=")
				.append(born).append(", died=").append(died);
		return builder.toString();
	}
	
}
