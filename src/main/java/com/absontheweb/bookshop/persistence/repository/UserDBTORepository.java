package com.absontheweb.bookshop.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.absontheweb.bookshop.persistence.model.UserDBTO;

public interface UserDBTORepository extends JpaRepository<UserDBTO, Long> {

	@Query("SELECT u FROM UserDBTO u WHERE LOWER(u.login) = LOWER(:login)")
	UserDBTO findByLoginCaseInsensitive(@Param("login") String login);

    @Query
    UserDBTO findByEmail(String email);

    @Query
    UserDBTO findByEmailAndActivationKey(String email, String activationKey);

}