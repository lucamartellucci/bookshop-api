package io.lucci.bookshop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.lucci.bookshop.security.AuthorityHandler;

@Configuration
public class AuthorityManagerTestConfig {

	@Bean
    public AuthorityHandler authorityManager() {
        return new AuthorityHandler();
    }
}
