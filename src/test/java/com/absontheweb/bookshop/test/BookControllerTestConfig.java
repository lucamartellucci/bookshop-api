package com.absontheweb.bookshop.test;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.absontheweb.bookshop.application.AbstractWebConfig;
import com.absontheweb.bookshop.service.BookService;

@Configuration
@EnableWebMvc
@ComponentScan (basePackages={
		"com.absontheweb.bookshop.book",
		"com.absontheweb.bookshop.controller"
})
public class BookControllerTestConfig extends AbstractWebConfig {
	
	@Bean
    public BookService bookService() {
        return Mockito.mock( BookService.class );
    }

}
