package io.lucci.bookshop.test.base;

import java.util.List;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.lucci.bookshop.book.controller.UserArgumentResolverMock;

import io.lucci.bookshop.application.AbstractWebConfig;
import io.lucci.bookshop.controller.resolver.PaginatorArgumentResolver;
import io.lucci.bookshop.service.BookService;

@Configuration
@EnableWebMvc
@ComponentScan (basePackages={
		"io.lucci.bookshop.book",
		"io.lucci.bookshop.controller"
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
