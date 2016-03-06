package com.absontheweb.bookshop.i18n.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import lombok.Data;

@Data
public class MessageResourceLocale implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Map<String, Properties> propertiesMap= new HashMap<String, Properties>();
	private List<Language> supportedLanguages = new ArrayList<Language>();
	
}
