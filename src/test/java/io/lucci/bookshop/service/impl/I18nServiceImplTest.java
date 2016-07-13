package io.lucci.bookshop.service.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.lucci.bookshop.i18n.model.Language;
import io.lucci.bookshop.i18n.model.MessageResourceLocale;
import io.lucci.bookshop.service.exception.I18nServiceException;
import io.lucci.bookshop.service.impl.I18nServiceImpl;


public class I18nServiceImplTest {
	
	private I18nServiceImpl i18nServiceImpl;
	private static Logger logger = LoggerFactory.getLogger(I18nServiceImplTest.class);
	
	@Before
	public void setUp() throws Exception {
		this.i18nServiceImpl = new I18nServiceImpl();
		this.i18nServiceImpl.setFlagTemplateUrl("http://localhost:8080/assets/{locale}_flag.png");
		this.i18nServiceImpl.setResourcePath("/i18n");
		this.i18nServiceImpl.afterPropertiesSet();
	}

	@Test
	public void testGetLocale() throws Exception {
		assertThat(this.i18nServiceImpl.getLocale("message_IT.properties"), is("IT"));
		try {
			this.i18nServiceImpl.getLocale("message.properties");
			fail();
		} catch (Exception e) {
		}
	}

	@Test
	public void testBuildFlagUrl() throws Exception {
		assertThat(this.i18nServiceImpl.buildFlagUrl("IT"),is("http://localhost:8080/assets/IT_flag.png"));
		assertThat(this.i18nServiceImpl.buildFlagUrl("FR"),is("http://localhost:8080/assets/FR_flag.png"));
	}

	@Test
	public void testRetrieveSupportedLanguages() throws Exception {
		List<Language> supportedLanguages = this.i18nServiceImpl.retrieveSupportedLanguages();
		assertThat(supportedLanguages.size(),is(2));

		Language language = supportedLanguages.get(0);
		assertThat(language.getName(),is("English"));
		assertThat(language.getLocale(),is("EN"));
		assertThat(language.getFlagUrl(),is("http://localhost:8080/assets/EN_flag.png"));
		
		language = supportedLanguages.get(1);
		assertThat(language.getName(),is("Italiano"));
		assertThat(language.getLocale(),is("IT"));
		assertThat(language.getFlagUrl(),is("http://localhost:8080/assets/IT_flag.png"));
		
	}

	@Test
	public void testRetrieveMessageResourceLocale() throws Exception {
		MessageResourceLocale messageResourceLocale = i18nServiceImpl.retrieveMessageResourceLocale();
		assertThat(messageResourceLocale.getPropertiesMap().size(), is(2));
		assertThat(messageResourceLocale.getSupportedLanguages().size(), is(2));
		assertThat(messageResourceLocale.getPropertiesMap().get("IT").get("error.validation.title.maxlength"), is("Lunghezza massima di 150 caratteri"));
		assertThat(messageResourceLocale.getPropertiesMap().get("EN").get("error.validation.title.maxlength"), is("Title is too long"));
	}
	
	@Test
	public void testDate() throws Exception {
		LocalDate date = LocalDate.of(2015, Month.JANUARY, 29);
		date = date.plusWeeks(40);
		logger.info("Delivery date is: {}",date);
	}

	@Test(expected=I18nServiceException.class)
	public void testBuildMessageResourceLocale() throws Exception {
		this.i18nServiceImpl.buildMessageResourceLocale("fakepath");
	}
	

}
