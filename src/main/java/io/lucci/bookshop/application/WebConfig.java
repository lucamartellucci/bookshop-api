package io.lucci.bookshop.application;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages={
		"io.lucci.bookshop.book",
		"io.lucci.bookshop.user",
		"io.lucci.bookshop.i18n",
		"io.lucci.bookshop.controller",
		"io.lucci.bookshop.service"
})
@EnableWebMvc
public class WebConfig extends AbstractWebConfig {

	
   
}