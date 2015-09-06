package com.absontheweb.bookshop.service;

import java.util.List;

import com.absontheweb.bookshop.model.i10l.Language;
import com.absontheweb.bookshop.model.i18n.MessageResource;
import com.absontheweb.bookshop.service.exception.I18nServiceException;

public interface I18nService {
	
	public MessageResource retrieveMessageResource() throws I18nServiceException; 
	
	public List<Language> retrieveSupportedLanguages() throws I18nServiceException;
	
}
