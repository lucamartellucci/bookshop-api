package com.absontheweb.bookshop.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.absontheweb.bookshop.persistence.model.UserDBTO;

@Transactional(propagation = Propagation.REQUIRED)
public interface UserDBTORepository extends JpaRepository<UserDBTO, Long> {


}