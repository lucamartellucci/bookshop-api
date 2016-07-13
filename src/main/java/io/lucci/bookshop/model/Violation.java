package io.lucci.bookshop.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Violation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String field;
	private Object rejectedValue;
	private String code;
	private String objectName;
	private String message;
	
	public Violation() {
		super();
	}
	
	public Violation(String field, Object rejectedValue, String objectName, String code, String message) {
		super();
		this.field = field;
		this.rejectedValue = rejectedValue;
		this.objectName = objectName;
		this.code = code;
		this.message = message;
	}
	
	public Violation(String field, Object rejectedValue, String objectName, String code) {
		super();
		this.field = field;
		this.rejectedValue = rejectedValue;
		this.objectName = objectName;
		this.code = code;
	}

}
