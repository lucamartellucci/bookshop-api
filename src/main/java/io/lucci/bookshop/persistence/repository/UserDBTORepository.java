package io.lucci.bookshop.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.lucci.bookshop.persistence.model.UserDBTO;

public interface UserDBTORepository extends JpaRepository<UserDBTO, Long> {

	@Query("SELECT u FROM UserDBTO u WHERE LOWER(u.username) = LOWER(:username)")
	UserDBTO findByLoginCaseInsensitive(@Param("username") String username);

    @Query
    UserDBTO findByEmail(String email);
    
}