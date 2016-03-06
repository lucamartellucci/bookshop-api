package com.absontheweb.bookshop.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/*
 * An authority (a security role) used by Spring Security.
 */
@Data
@Entity
@Table(name = "authority")
public class AuthorityDBTO {

    @Id
    @Column(length = 50)
    private String name;

    
    
}
