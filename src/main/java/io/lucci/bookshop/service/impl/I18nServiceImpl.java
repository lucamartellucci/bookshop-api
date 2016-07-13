package io.lucci.bookshop.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.collect.ImmutableMap;

import io.lucci.bookshop.i18n.model.Language;
import io.lucci.bookshop.i18n.model.MessageResourceLocale;
import io.lucci.bookshop.service.I18nService;
import io.lucci.bookshop.service.exception.I18nServiceException;

@Service
public class I18nServiceImpl implements I18nService, InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(I18nServiceImpl.class);

	@Value("${i18n.flagTemplateUrl}")
	private String flagTemplateUrl;
	
	@Value("${i18n.resourcePath}")
	private String resourcePath;
	
	private UriComponentsBuilder uriBuilder;

	private MessageResourceLocale messageResourceLocale;

	@Override
	public MessageResourceLocale retrieveMessageResourceLocale() throws I18nServiceException {
		return messageResourceLocale;
	}
	
	@Override
	public List<Language> retrieveSupportedLanguages() {
		return messageResourceLocale.getSupportedLanguages();
	}
	
	protected MessageResourceLocale buildMessageResourceLocale(String resourcePath) throws I18nServiceException {
		MessageResourceLocale mrl = new MessageResourceLocale();
		try {
			
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = resolver.getResources(resourcePath.concat("/*.properties"));

	        for (Resource resource : resources) {
	            Properties props = new Properties();
				props.load(resource.getInputStream());
				String languageCode = getLocale(resource.getFilename());
				mrl.getPropertiesMap().put(languageCode, props);
				mrl.getSupportedLanguages()
					.add(new Language(props.getProperty("languageName"), languageCode, buildFlagUrl(languageCode)));
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

	protected String buildFlagUrl(String languageCode) throws MalformedURLException {
		return this.uriBuilder.buildAndExpand(ImmutableMap.of("locale",languageCode)).toString();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		uriBuilder = UriComponentsBuilder.fromUriString(flagTemplateUrl);
		messageResourceLocale = buildMessageResourceLocale(resourcePath);
	}

	public void setFlagTemplateUrl(String flagTemplateUrl) {
		this.flagTemplateUrl = flagTemplateUrl;
	}
	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}
	
}
