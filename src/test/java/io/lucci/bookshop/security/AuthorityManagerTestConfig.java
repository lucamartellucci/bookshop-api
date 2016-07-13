package io.lucci.bookshop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.lucci.bookshop.security.AuthorityManager;

@Configuration
public class AuthorityManagerTestConfig {

	@Bean
    public AuthorityManager authorityManager() {
        return new AuthorityManager();
    }
}
