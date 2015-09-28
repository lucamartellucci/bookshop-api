package com.absontheweb.bookshop.application;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages={
		"com.absontheweb.bookshop.book",
		"com.absontheweb.bookshop.i18n",
		"com.absontheweb.bookshop.controller",
		"com.absontheweb.bookshop.service",
		"com.absontheweb.bookshop.filter"
})
@EnableWebMvc
public class WebConfig extends AbstractWebConfig {

	public WebConfig() {
        super();
    }
   
}