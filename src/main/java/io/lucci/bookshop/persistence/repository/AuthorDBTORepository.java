package io.lucci.bookshop.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.lucci.bookshop.persistence.model.AuthorDBTO;

public interface AuthorDBTORepository extends JpaRepository<AuthorDBTO, Long> {

}