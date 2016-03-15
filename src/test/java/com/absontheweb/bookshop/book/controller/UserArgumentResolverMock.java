package com.absontheweb.bookshop.book.controller;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.absontheweb.bookshop.controller.resolver.CurrentUser;
import com.absontheweb.bookshop.model.User;

public class UserArgumentResolverMock implements HandlerMethodArgumentResolver {
	
	private static User user;

    public boolean supportsParameter( MethodParameter methodParameter ) {
        return methodParameter.getParameterAnnotation( CurrentUser.class ) != null;
    }

    public Object resolveArgument( MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory ) throws Exception {
        return user;
    }


    public static void setUser( final User user ) {
        UserArgumentResolverMock.user = user;
    }
}
