package com.absontheweb.bookshop.i18n.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.absontheweb.bookshop.controller.exception.InternalServerErrorException;
import com.absontheweb.bookshop.controller.exception.ResourceNotFoundException;
import com.absontheweb.bookshop.i18n.model.Language;
import com.absontheweb.bookshop.i18n.model.MessageResourceLocale;
import com.absontheweb.bookshop.service.I18nService;
import com.absontheweb.bookshop.service.exception.I18nServiceException;


@RestController
@RequestMapping("/api/i18n")
public class I18nController {
	
	@Autowired
	private I18nService i18nService;

	private static Logger logger = LoggerFactory.getLogger(I18nController.class);

	@RequestMapping ( method = RequestMethod.GET, value = "/languages" )
    public @ResponseBody List<Language> getLanguages() throws InternalServerErrorException {
        logger.debug( "Retrieve all available languages" );
        try {
            return i18nService.retrieveSupportedLanguages();
        } catch ( Exception e ) {
            throw new InternalServerErrorException( "Unable to retrieve languages", e );
        }
    }
	
	@RequestMapping ( method = RequestMethod.GET, value = "/messages/{language}" )
    public @ResponseBody Map<String,String> getMessages( @PathVariable String language ) throws InternalServerErrorException, ResourceNotFoundException {

        logger.info( "Get messages for language [{}]", language );
        MessageResourceLocale messageResourceLocale;
        try {
            messageResourceLocale = i18nService.retrieveMessageResourceLocale();
        } catch ( I18nServiceException e ) {
        	logger.error("Unable to retrieve the message resource for language {}", language, e);
            throw new InternalServerErrorException("Unable to retrieve the message resource for language ".concat(language), e);
        }
        return buildMessages( language, messageResourceLocale );
    }


    protected Map<String, String> buildMessages( final String language, final MessageResourceLocale messageResourceLocale  ) {
    	Map<String, String> result = new LinkedHashMap<String, String>();
        Properties properties = messageResourceLocale.getPropertiesMap().get( language.toUpperCase() );
        if ( properties != null ) {
            for ( Map.Entry<Object, Object> objectEntry : properties.entrySet() ) {
                result.put( "" + objectEntry.getKey(), "" + objectEntry.getValue() );
            }
        }
        return result;
    }
	
	
}
