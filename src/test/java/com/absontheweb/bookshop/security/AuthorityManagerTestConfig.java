package com.absontheweb.bookshop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorityManagerTestConfig {

	@Bean
    public AuthorityManager authorityManager() {
        return new AuthorityManager();
    }
}
