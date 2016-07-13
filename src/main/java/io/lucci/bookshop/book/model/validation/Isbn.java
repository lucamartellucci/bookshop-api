package io.lucci.bookshop.book.model.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsbnValidator.class)
@Documented
public @interface Isbn {
	
	String message() default "{error.validation.isbn.notvalid}";
	 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};

}
