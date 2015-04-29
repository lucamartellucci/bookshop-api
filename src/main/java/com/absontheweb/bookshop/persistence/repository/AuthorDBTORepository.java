package com.absontheweb.bookshop.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.absontheweb.bookshop.persistence.model.AuthorDBTO;

public interface AuthorDBTORepository extends JpaRepository<AuthorDBTO, Long> {

}