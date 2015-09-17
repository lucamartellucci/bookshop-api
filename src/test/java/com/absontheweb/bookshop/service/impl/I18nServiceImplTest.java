package com.absontheweb.bookshop.service.impl;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


public class I18nServiceImplTest {
	
	private I18nServiceImpl i18nServiceImpl;
	
	@Before
	public void setup() throws Exception {
		this.i18nServiceImpl = new I18nServiceImpl();
		this.i18nServiceImpl.setFlagTemplateUrl("http://localhost:8080/assets/{locale}_flag.png");
		this.i18nServiceImpl.setResourcePath("src/main/resources/i18n/");
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

}
