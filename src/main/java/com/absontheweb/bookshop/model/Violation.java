package com.absontheweb.bookshop.model;

import java.io.Serializable;

public class Violation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String field;
	private Object rejectedValue;
	private String code;
	private String objectName;
	
	public Violation() {
		super();
	}
	
	public Violation(String field, Object rejectedValue, String objectName, String code) {
		super();
		this.field = field;
		this.rejectedValue = rejectedValue;
		this.objectName = objectName;
		this.code = code;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public Object getRejectedValue() {
		return rejectedValue;
	}
	public void setRejectedValue(Object rejectedValue) {
		this.rejectedValue = rejectedValue;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + ((objectName == null) ? 0 : objectName.hashCode());
		result = prime * result
				+ ((rejectedValue == null) ? 0 : rejectedValue.hashCode());
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
		Violation other = (Violation) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (objectName == null) {
			if (other.objectName != null)
				return false;
		} else if (!objectName.equals(other.objectName))
			return false;
		if (rejectedValue == null) {
			if (other.rejectedValue != null)
				return false;
		} else if (!rejectedValue.equals(other.rejectedValue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Violation [field=");
		builder.append(field);
		builder.append(", rejectedValue=");
		builder.append(rejectedValue);
		builder.append(", code=");
		builder.append(code);
		builder.append(", objectName=");
		builder.append(objectName);
		builder.append("]");
		return builder.toString();
	}
	
}
