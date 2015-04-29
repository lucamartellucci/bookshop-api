package com.absontheweb.bookshop.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.absontheweb.bookshop.persistence.model.UserDBTO;

public interface UserDBTORepository extends JpaRepository<UserDBTO, Long> {


}