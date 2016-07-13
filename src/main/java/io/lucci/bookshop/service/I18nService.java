package io.lucci.bookshop.service;

import java.util.List;

import io.lucci.bookshop.i18n.model.Language;
import io.lucci.bookshop.i18n.model.MessageResourceLocale;
import io.lucci.bookshop.service.exception.I18nServiceException;

public interface I18nService {
	
	public MessageResourceLocale retrieveMessageResourceLocale() throws I18nServiceException; 
	
	public List<Language> retrieveSupportedLanguages() throws I18nServiceException;
	
}
