package com.absontheweb.bookshop.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.absontheweb.bookshop.web.controller.resolver.PaginatorArgumentResolver;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ComponentScan("com.absontheweb.bookshop.web.controller")
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

	public WebConfig() {
        super();
    }
	
	@Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
//        validatorFactoryBean.setValidationMessageSource(messageSource);
        return validatorFactoryBean;
    }
 
    @Override
    public Validator getValidator() {
        return validator();
    }
    
    @Override
    public void addArgumentResolvers(List< HandlerMethodArgumentResolver > argumentResolvers) {
//    	UserArgumentResolver personResolver = new UserArgumentResolver();
//    	argumentResolvers.add(personResolver);
    	PaginatorArgumentResolver pageableResolver = new PaginatorArgumentResolver();
    	argumentResolvers.add(pageableResolver);
    }

    @Override
    public void configureMessageConverters( final List<HttpMessageConverter<?>> converters ) {

        final ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        byteArrayHttpMessageConverter.setSupportedMediaTypes( Arrays.asList( MediaType.APPLICATION_OCTET_STREAM ) );
        converters.add( byteArrayHttpMessageConverter );

        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion( JsonInclude.Include.NON_NULL );

        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes( Arrays.asList( MediaType.APPLICATION_JSON ) );
        converter.setObjectMapper( mapper );
        converters.add( converter );

        super.configureMessageConverters( converters );
    }
    
   
}