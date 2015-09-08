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
import com.absontheweb.bookshop.i18n.model.MessageResource;
import com.absontheweb.bookshop.i18n.model.MessageResourceLocale;
import com.absontheweb.bookshop.service.I18nService;
import com.absontheweb.bookshop.service.exception.I18nServiceException;


@RestController
@RequestMapping("/api/i18n")
public class I18nController {
	
	@Autowired
	I18nService i18nService;

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
        MessageResource messageResource;
        try {
            messageResource = i18nService.retrieveMessageResource();
        } catch ( I18nServiceException e ) {
            throw new InternalServerErrorException();
        }

        final Map<String, String> result = new LinkedHashMap<String, String>();
        buildMessages( language, messageResource, result );
        return result;
    }


    protected void buildMessages( final String language, final MessageResource messageResource, final Map<String, String> result ) {
        final Map<String,MessageResourceLocale> nameSpaceMap = messageResource.getNameSpaceMap();
        for ( Map.Entry<String, MessageResourceLocale> entry : nameSpaceMap.entrySet() ) {
            final String prefix = entry.getKey();

            final MessageResourceLocale messageResourceLocale = entry.getValue();
            Properties properties = messageResourceLocale.getPropertiesMap().get( language.toUpperCase() );
            if ( properties != null ) {
                for ( Map.Entry<Object, Object> objectEntry : properties.entrySet() ) {
                    result.put( String.format( "%s.%s", prefix, objectEntry.getKey() ), "" + objectEntry.getValue() );
                }
            }
        }
    }

	
	
}
