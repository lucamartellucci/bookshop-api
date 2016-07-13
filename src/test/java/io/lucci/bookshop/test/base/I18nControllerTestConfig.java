package io.lucci.bookshop.test.base;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.lucci.bookshop.application.AbstractWebConfig;
import io.lucci.bookshop.service.I18nService;

@Configuration
@EnableWebMvc
@ComponentScan (basePackages={
		"io.lucci.bookshop.i18n",
		"io.lucci.bookshop.controller" // for exception handling
})
public class I18nControllerTestConfig extends AbstractWebConfig {
	
	@Bean
    public I18nService bookService() {
        return Mockito.mock( I18nService.class );
    }

}
