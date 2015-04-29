package com.absontheweb.bookshop.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.absontheweb.bookshop.persistence.model.BookDBTO;

public interface BookDBTORepository extends JpaRepository<BookDBTO, Long> {

	BookDBTO findByTitle(String title);

}