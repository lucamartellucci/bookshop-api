package com.absontheweb.bookshop.model.i18n;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MessageResourceLocale implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Map<String, Properties> propertiesMap= new HashMap<String, Properties>();

	public Map<String, Properties> getPropertiesMap() {
		return propertiesMap;
	}

	public void setPropertiesMap(Map<String, Properties> propertiesMap) {
		this.propertiesMap = propertiesMap;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageResourceLocale [propertiesMap=");
		builder.append(propertiesMap);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((propertiesMap == null) ? 0 : propertiesMap.hashCode());
		return result;
	}
	
	
	
	
	
	
}
