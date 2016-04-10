package com.absontheweb.bookshop.security;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AuthorityManagerTestConfig {

	@Bean
    public Environment bookService() {
        return Mockito.mock( Environment.class );
    }
}
