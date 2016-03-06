package com.absontheweb.bookshop.test;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.absontheweb.bookshop.application.AbstractWebConfig;
import com.absontheweb.bookshop.service.I18nService;

@Configuration
@EnableWebMvc
@ComponentScan (basePackages={
		"com.absontheweb.bookshop.i18n",
		"com.absontheweb.bookshop.controller" // for exception handling
})
public class I18nControllerTestConfig extends AbstractWebConfig {
	
	@Bean
    public I18nService bookService() {
        return Mockito.mock( I18nService.class );
    }

}
