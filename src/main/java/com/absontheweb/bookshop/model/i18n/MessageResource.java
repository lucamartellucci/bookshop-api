package com.absontheweb.bookshop.model.i18n;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MessageResource implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, MessageResourceLocale> nameSpaceMap=new HashMap<String, MessageResourceLocale>();

	public Map<String, MessageResourceLocale> getNameSpaceMap() {
		return nameSpaceMap;
	}

	public void setNameSpaceMap(Map<String, MessageResourceLocale> nameSpaceMap) {
		this.nameSpaceMap = nameSpaceMap;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageResource [nameSpaceMap=");
		builder.append(nameSpaceMap);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nameSpaceMap == null) ? 0 : nameSpaceMap.hashCode());
		return result;
	}

	
	
	

}
