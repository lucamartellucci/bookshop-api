package com.absontheweb.bookshop.model;

import lombok.Data;

@Data
public class User {

    private Long id;

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private boolean activated;

    private String langKey;

    private String activationKey;

}
