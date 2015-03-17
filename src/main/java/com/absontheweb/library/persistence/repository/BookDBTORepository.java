package com.absontheweb.library.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.absontheweb.library.persistence.model.BookDBTO;

@Transactional(propagation = Propagation.REQUIRED)
public interface BookDBTORepository extends JpaRepository<BookDBTO, Long> {

	BookDBTO findByTitle(String title);

}