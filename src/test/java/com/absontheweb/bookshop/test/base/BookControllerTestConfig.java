package com.absontheweb.bookshop.test.base;

import java.util.List;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.absontheweb.bookshop.application.AbstractWebConfig;
import com.absontheweb.bookshop.book.controller.UserArgumentResolverMock;
import com.absontheweb.bookshop.controller.resolver.PaginatorArgumentResolver;
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
	
	@Override
	public void addArgumentResolvers( List<HandlerMethodArgumentResolver> argumentResolvers ) {
        argumentResolvers.add( new UserArgumentResolverMock() );
        PaginatorArgumentResolver pageableResolver = new PaginatorArgumentResolver();
    	argumentResolvers.add(pageableResolver);
    }

}
