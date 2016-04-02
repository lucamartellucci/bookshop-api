package com.absontheweb.bookshop.application;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.absontheweb.bookshop.controller.resolver.PaginatorArgumentResolver;
import com.absontheweb.bookshop.controller.resolver.UserArgumentResolver;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class AbstractWebConfig extends WebMvcConfigurerAdapter {

	@Autowired
    private Environment env;
	
    @Autowired
    private MessageSource messageSource;
    
	@Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
    	if (Boolean.parseBoolean(env.getProperty("validation.resolveErrorCodes"))) {
    		validatorFactoryBean.setValidationMessageSource(messageSource);
    	}
        return validatorFactoryBean;
    }
 
    @Override
    public Validator getValidator() {
        return validator();
    }
    
    @Override
    public void addArgumentResolvers(List< HandlerMethodArgumentResolver > argumentResolvers) {
    	UserArgumentResolver personResolver = new UserArgumentResolver();
    	argumentResolvers.add(personResolver);
    	PaginatorArgumentResolver pageableResolver = new PaginatorArgumentResolver();
    	argumentResolvers.add(pageableResolver);
    }

    @Override
    public void configureMessageConverters( final List<HttpMessageConverter<?>> converters ) {

        final ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        byteArrayHttpMessageConverter.setSupportedMediaTypes( Arrays.asList( MediaType.APPLICATION_OCTET_STREAM ) );
        converters.add( byteArrayHttpMessageConverter );

        final ObjectMapper mapper = Jackson2ObjectMapperBuilder.json()
	        .serializationInclusion(JsonInclude.Include.NON_NULL) // Don’t include null values
	        .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) //ISODate
	        .build();
        
        final MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setSupportedMediaTypes( Arrays.asList( MediaType.APPLICATION_JSON ) );
        jsonConverter.setObjectMapper( mapper );
        converters.add( jsonConverter );

        super.configureMessageConverters( converters );
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**");
    }
    
   
}