// CHECKSTYLE:OFF
/**
 * Source code generated by Fluent Builders Generator
 * Do not modify this file
 * See generator home page at: http://code.google.com/p/fluent-builders-generator-eclipse-plugin/
 */

package io.lucci.bookshop.model;

public class ViolationBuilder extends ViolationBuilderBase<ViolationBuilder> {
	public static ViolationBuilder violation() {
		return new ViolationBuilder();
	}

	public ViolationBuilder() {
		super(new Violation());
	}

	public Violation build() {
		return getInstance();
	}
}

class ViolationBuilderBase<T extends ViolationBuilderBase<T>> {
	private Violation instance;

	protected ViolationBuilderBase(Violation aInstance) {
		instance = aInstance;
	}

	protected Violation getInstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	public T withField(String aValue) {
		instance.setField(aValue);

		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withRejectedValue(Object aValue) {
		instance.setRejectedValue(aValue);

		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withObjectName(String aValue) {
		instance.setObjectName(aValue);

		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T withCode(String aValue) {
		instance.setCode(aValue);

		return (T) this;
	}
}