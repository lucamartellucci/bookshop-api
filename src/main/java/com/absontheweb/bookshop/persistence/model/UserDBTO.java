package com.absontheweb.bookshop.persistence.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

/*
 * A user.
 */
@Data
@Entity
@Table(name = "user")
public class UserDBTO {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 50, unique = true, nullable = false)
    private String username;

    @Column(length = 100)
    private String password;

    @Column(name = "first_name", length = 50)
    private String firstname;

    @Column(name = "last_name", length = 50)
    private String lastname;

    @Column(length = 100, unique = true)
    private String email;

    @Column(name="user_enabled" ,nullable = false)
    private boolean enabled;

    @Column(name = "lang_key", length = 5)
    private String langKey;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    private Set<AuthorityDBTO> authorities = new HashSet<AuthorityDBTO>();

}
