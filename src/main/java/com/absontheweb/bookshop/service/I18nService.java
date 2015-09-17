package com.absontheweb.bookshop.service;

import java.util.List;

import com.absontheweb.bookshop.i18n.model.Language;
import com.absontheweb.bookshop.i18n.model.MessageResourceLocale;
import com.absontheweb.bookshop.service.exception.I18nServiceException;

public interface I18nService {
	
	public MessageResourceLocale retrieveMessageResourceLocale() throws I18nServiceException; 
	
	public List<Language> retrieveSupportedLanguages() throws I18nServiceException;
	
}
