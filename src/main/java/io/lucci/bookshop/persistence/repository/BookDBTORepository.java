package io.lucci.bookshop.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.lucci.bookshop.persistence.model.BookDBTO;

public interface BookDBTORepository extends JpaRepository<BookDBTO, Long> {

	BookDBTO findByTitle(String title);

}