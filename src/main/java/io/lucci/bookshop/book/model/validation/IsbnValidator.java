package io.lucci.bookshop.book.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.validator.routines.ISBNValidator;

public class IsbnValidator implements ConstraintValidator<Isbn, String> {
	
	private Isbn isbn;

	@Override
	public void initialize(Isbn isbn) {
		this.isbn = isbn;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		
		if (ISBNValidator.getInstance().isValid(value)) {
			return true;
		}
		
		constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(isbn.message()).addConstraintViolation();
        return false;
	}

}
