package com.absontheweb.bookshop.web.controller;

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

import com.absontheweb.bookshop.model.i10l.Language;
import com.absontheweb.bookshop.model.i18n.MessageResource;
import com.absontheweb.bookshop.model.i18n.MessageResourceLocale;
import com.absontheweb.bookshop.service.I18nService;
import com.absontheweb.bookshop.service.exception.I18nServiceException;
import com.absontheweb.bookshop.web.controller.exception.InternalServerErrorException;
import com.absontheweb.bookshop.web.controller.exception.ResourceNotFoundException;


@RestController
@RequestMapping("/api/l10n")
public class L10nController {
	
	@Autowired
	I18nService i18nService;

	private static Logger logger = LoggerFactory.getLogger(L10nController.class);

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
