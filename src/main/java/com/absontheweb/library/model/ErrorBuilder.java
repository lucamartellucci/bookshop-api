// CHECKSTYLE:OFF
/**
 * Source code generated by Fluent Builders Generator
 * Do not modify this file
 * See generator home page at: http://code.google.com/p/fluent-builders-generator-eclipse-plugin/
 */

package com.absontheweb.library.model;

import java.util.Map;

public class ErrorBuilder extends ErrorBuilderBase<ErrorBuilder> {
	public static ErrorBuilder error() {
		return new ErrorBuilder();
	}

	public ErrorBuilder() {
		super(new Error());
	}

	public Error build() {
		return getInstance();
	}
}

class ErrorBuilderBase<GeneratorT extends ErrorBuilderBase<GeneratorT>> {
	private Error instance;

	protected ErrorBuilderBase(Error aInstance) {
		instance = aInstance;
	}

	protected Error getInstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	public GeneratorT withCode(String aValue) {
		instance.setCode(aValue);

		return (GeneratorT) this;
	}

	@SuppressWarnings("unchecked")
	public GeneratorT withMessage(String aValue) {
		instance.setMessage(aValue);

		return (GeneratorT) this;
	}

	@SuppressWarnings("unchecked")
	public GeneratorT withAdditionalParams(Map<String, String> aValue) {
		instance.setAdditionalParams(aValue);

		return (GeneratorT) this;
	}
}
