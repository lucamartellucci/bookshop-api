package com.absontheweb.bookshop.web.controller.exception;

import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.absontheweb.bookshop.model.Error;
import com.absontheweb.bookshop.model.ErrorBuilder;


@ControllerAdvice
public class RestResponseEntityExceptionHandler {

	@ExceptionHandler (value = { ResourceNotFoundException.class })
    @ResponseStatus (HttpStatus.NOT_FOUND)
    protected @ResponseBody Error handleAccessDeniedException( Exception ex, WebRequest request ) {
        return ErrorBuilder.error()
        		.withCode(ErrorCode.RESOURCE_NOT_FOUND)
        		.withMessage(ex.getMessage()).build();
    }


    @ExceptionHandler (value = { Exception.class, UndeclaredThrowableException.class })
    @ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
    protected @ResponseBody Error handleGenericExceptions( Exception ex, WebRequest request ) {
        return new Error( ErrorCode.GENERIC_ERROR, ex.getMessage() );
    }
    
    @ExceptionHandler (value = { InternalServerErrorException.class })
    @ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
    protected @ResponseBody Error handleInternalServerErrorExceptions( Exception ex, WebRequest request ) {
    	return new Error( ErrorCode.GENERIC_ERROR, ex.getCause().getMessage() );
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody Map<String, Object> handleValidationException(MethodArgumentNotValidException ex) throws IOException {
        Map<String, Object>  map = new HashMap<>();
        map.put("error", "Validation Failure");
        map.put("violations", convertConstraintViolation(ex));
        return map;
    }
    
 
    private Map<String, Map<String, Object> > convertConstraintViolation(MethodArgumentNotValidException ex) {
        Map<String, Map<String, Object> > result = new HashMap<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            Map<String, Object>  violationMap = new HashMap<>();
            violationMap.put("target", ex.getBindingResult().getTarget());
            violationMap.put("type", ex.getBindingResult().getTarget().getClass());
            violationMap.put("message", error.getDefaultMessage());
            result.put(error.getObjectName(), violationMap);
        }
        return result;
    }

}