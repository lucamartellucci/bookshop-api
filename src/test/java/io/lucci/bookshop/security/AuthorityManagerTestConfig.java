package io.lucci.bookshop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorityManagerTestConfig {

	@Bean
    public AuthorityHandler authorityManager() {
        return new AuthorityHandler();
    }
}
