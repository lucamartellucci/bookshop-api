package com.absontheweb.bookshop.service.impl;

import java.io.File;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import com.absontheweb.bookshop.i18n.model.Language;
import com.absontheweb.bookshop.i18n.model.MessageResourceLocale;
import com.absontheweb.bookshop.service.I18nService;
import com.absontheweb.bookshop.service.exception.I18nServiceException;
import com.google.common.collect.ImmutableMap;

@Service
public class I18nServiceImpl implements I18nService, InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(I18nServiceImpl.class);

	@Value("${i18n.flag.template.url}")
	private String flagTemplateUrl;
	
	@Value("${i18n.resource.path}")
	private String resourcePath;
	
	private UriComponentsBuilder uriBuilder;

	private MessageResourceLocale messageResourceLocale;
	private List<Language> supportedLanguages;

	@Override
	public MessageResourceLocale retrieveMessageResourceLocale() throws I18nServiceException {
		return messageResourceLocale;
	}
	
	@Override
	public List<Language> retrieveSupportedLanguages() {
		return supportedLanguages;
	}

	protected MessageResourceLocale buildMessageResourceLocale() throws I18nServiceException {
		MessageResourceLocale mrl = new MessageResourceLocale();
		try {
			File directory = new File(resourcePath);
			if (!directory.isDirectory()) {
				throw new I18nServiceException(resourcePath + " is not directory");
			}
			if (!directory.exists()) {
				throw new I18nServiceException(resourcePath + " not found");
			}
			
			Collection<File> propertyFiles = FileUtils.listFiles(directory, new RegexFileFilter("^.+\\.properties"), DirectoryFileFilter.DIRECTORY);
			for (File propertyFile : propertyFiles) {
				Properties props = new Properties();
				props.load(new FileReader(propertyFile));
				mrl.getPropertiesMap().put(getLocale(propertyFile.getName()), props);
			}
			return mrl;
		} catch (Exception e) {
			logger.error("Unable to build message resource locale: ", e);
			throw new I18nServiceException(e);
		}
	}

	protected String getLocale(String fileName) throws Exception {
		if (!fileName.matches("[a-z0-9]*_[a-zA-Z]{2}.properties")){
			throw new Exception("File ".concat(fileName).concat("doesn't match the filename_locale.properties pattern"));
		}
		return StringUtils.stripFilenameExtension(fileName).substring(fileName.length()-13).toUpperCase();
	}

	protected List<Language> buildSupportedLanguages() throws I18nServiceException {
		try {
			List<Language> newSupportedLanguages = new ArrayList<Language>();
			if (messageResourceLocale != null && messageResourceLocale.getPropertiesMap() != null) {
				Map<String, Properties> propertiesMap = messageResourceLocale.getPropertiesMap();
				for (String languageCode : propertiesMap.keySet()) {
					if (!"DEFAULT".equalsIgnoreCase(languageCode)) {
							Properties properties = messageResourceLocale.getPropertiesMap().get(languageCode);
							String languageName = properties.getProperty("languageName");
							Locale languageLocale = new Locale(languageCode);
							Language toAdd = new Language(languageName, languageLocale.getLanguage(), buildFlagUrl(languageCode));
							newSupportedLanguages.add(toAdd);
					}
				}
			}
			return newSupportedLanguages;
		} catch (Exception e) {
			logger.error("Unable to build message resource locale: ", e);
			throw new I18nServiceException(e);
		}
	}

	protected String buildFlagUrl(String languageCode) throws MalformedURLException {
		return this.uriBuilder.buildAndExpand(ImmutableMap.of("locale",languageCode)).toString();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.uriBuilder = UriComponentsBuilder.fromUriString(this.flagTemplateUrl);
		this.messageResourceLocale = buildMessageResourceLocale();
		this.supportedLanguages = buildSupportedLanguages();
	}

	public void setFlagTemplateUrl(String flagTemplateUrl) {
		this.flagTemplateUrl = flagTemplateUrl;
	}
	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}
	
}
