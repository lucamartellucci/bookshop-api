package com.absontheweb.bookshop.controller.exception;

import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.absontheweb.bookshop.model.Error;
import com.absontheweb.bookshop.model.ErrorBuilder;
import com.absontheweb.bookshop.model.Violation;
import com.absontheweb.bookshop.model.ViolationBuilder;


@ControllerAdvice
public class RestResponseEntityExceptionHandler {

	@ExceptionHandler (value = { ResourceNotFoundException.class })
    @ResponseStatus (HttpStatus.NOT_FOUND)
    protected @ResponseBody Error handleResourceNotFoundException( Exception ex, WebRequest request ) {
        return ErrorBuilder.error()
        		.withCode(ErrorCode.RESOURCE_NOT_FOUND)
        		.withMessage(ex.getMessage()).build();
    }
	
	@ExceptionHandler (value = { AccessDeniedException.class })
    @ResponseStatus (HttpStatus.UNAUTHORIZED)
    protected @ResponseBody Error handleAccessDeniedException( Exception ex, WebRequest request ) {
        return ErrorBuilder.error()
        		.withCode(ErrorCode.ACCESS_DENIED)
        		.withMessage(ex.getMessage()).build();
    }

    @ExceptionHandler (value = { InternalServerErrorException.class, Exception.class, UndeclaredThrowableException.class })
    @ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
    protected @ResponseBody Error handleInternalServerErrorExceptions( Exception ex, WebRequest request ) {
    	if (ex instanceof InternalServerErrorException) {
    		return new Error( ErrorCode.GENERIC_ERROR, ex.getCause().getMessage() );
    	} else {
    		return new Error( ErrorCode.GENERIC_ERROR, ex.getMessage() );
    	}
    	
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody Error handleValidationException(MethodArgumentNotValidException ex) throws IOException {
    	Error error = new Error();
    	error.setCode(ErrorCode.VALIDATION_ERROR);
    	error.setMessage("Validation Failure");
    	error.setViolations(convertConstraintViolation(ex));
        return error;
    }
    
 
    protected List<Violation> convertConstraintViolation(MethodArgumentNotValidException ex) {
    	List<Violation> result = new ArrayList<Violation>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
        	FieldError fieldError = (FieldError) error;
        	
            Violation  violation = ViolationBuilder.violation()
        		.withField(fieldError.getField())
        		.withRejectedValue(fieldError.getRejectedValue())
        		.withCode(extractCode(fieldError.getDefaultMessage()))
        		.withObjectName(fieldError.getObjectName())
        		.build();
            result.add(violation);
        }
        return result;
    }

    protected String extractCode(String message) {
		if (!StringUtils.isEmpty(message) && message.startsWith("{") && message.endsWith("}")){
			StringBuilder sb = new StringBuilder(message);
			sb.deleteCharAt(message.length()-1);
			sb.deleteCharAt(0);
			return sb.toString();
		} else {
			return message;
		}
	}

}