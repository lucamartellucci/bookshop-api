package com.absontheweb.bookshop.service;

import java.util.List;

import com.absontheweb.bookshop.i18n.model.Language;
import com.absontheweb.bookshop.i18n.model.MessageResource;
import com.absontheweb.bookshop.service.exception.I18nServiceException;

public interface I18nService {
	
	public MessageResource retrieveMessageResource() throws I18nServiceException; 
	
	public List<Language> retrieveSupportedLanguages() throws I18nServiceException;
	
}
