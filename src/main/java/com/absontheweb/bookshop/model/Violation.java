package com.absontheweb.bookshop.model;

import java.io.Serializable;

public class Violation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String field;
	private Object rejectedValue;
	private String message;
	
	public Violation() {
		super();
	}
	
	public Violation(String field, Object rejectedValue, String message) {
		super();
		this.field = field;
		this.rejectedValue = rejectedValue;
		this.message = message;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Violation [field=").append(field)
				.append(", rejectedValue=").append(rejectedValue)
				.append(", message=").append(message).append("]");
		return builder.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
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
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (rejectedValue == null) {
			if (other.rejectedValue != null)
				return false;
		} else if (!rejectedValue.equals(other.rejectedValue))
			return false;
		return true;
	}
	
}
