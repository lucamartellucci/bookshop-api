package com.absontheweb.bookshop.i18n.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class MessageResourceLocale implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Map<String, Properties> propertiesMap= new HashMap<String, Properties>();
	private List<Language> supportedLanguages = new ArrayList<Language>();
	

	public Map<String, Properties> getPropertiesMap() {
		return propertiesMap;
	}

	public void setPropertiesMap(Map<String, Properties> propertiesMap) {
		this.propertiesMap = propertiesMap;
	}

	public List<Language> getSupportedLanguages() {
		return supportedLanguages;
	}

	public void setSupportedLanguages(List<Language> supportedLanguages) {
		this.supportedLanguages = supportedLanguages;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageResourceLocale [propertiesMap=");
		builder.append(propertiesMap);
		builder.append(", supportedLanguages=");
		builder.append(supportedLanguages);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((propertiesMap == null) ? 0 : propertiesMap.hashCode());
		result = prime
				* result
				+ ((supportedLanguages == null) ? 0 : supportedLanguages
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageResourceLocale other = (MessageResourceLocale) obj;
		if (propertiesMap == null) {
			if (other.propertiesMap != null)
				return false;
		} else if (!propertiesMap.equals(other.propertiesMap))
			return false;
		if (supportedLanguages == null) {
			if (other.supportedLanguages != null)
				return false;
		} else if (!supportedLanguages.equals(other.supportedLanguages))
			return false;
		return true;
	}
}
