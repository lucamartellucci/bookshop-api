package com.absontheweb.library.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.absontheweb.library.persistence.model.AuthorDBTO;

@Transactional(propagation = Propagation.REQUIRED)
public interface AuthorDBTORepository extends JpaRepository<AuthorDBTO, Long> {

}