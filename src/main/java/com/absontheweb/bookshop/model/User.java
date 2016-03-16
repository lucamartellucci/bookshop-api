package com.absontheweb.bookshop.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class User {
	
	public User(){
		this.roles = new ArrayList<>();
	}

    private Long id;

    private String username;

    private String password;

    private String firstname;

    private String lastname;

    private String email;

    private boolean enabled;

    private String langKey;

    private List<String> roles;

}
