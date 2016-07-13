package io.lucci.bookshop.controller.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import io.lucci.bookshop.security.SimpleUserDetails;

public class UserArgumentResolver implements HandlerMethodArgumentResolver {
	
	
	private static final Logger logger = LoggerFactory.getLogger(UserArgumentResolver.class);

	@Override
    public boolean supportsParameter( MethodParameter methodParameter ) {
        return methodParameter.getParameterAnnotation( CurrentUser.class ) != null;
    }

    @Override
    public Object resolveArgument( MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory ) throws Exception {
        logger.debug( "resolving argument of methodParameter: {}", methodParameter );
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.debug("Authentication.getPrincipal() = {}",principal);
        SimpleUserDetails simpleUserDetail = null;
        if ( !( principal instanceof SimpleUserDetails ) ) {
            logger.debug( "Details of Authentication is not a SimpleUserDetails" );
            return null;
        } else {
        	simpleUserDetail = ( SimpleUserDetails ) principal;
            logger.debug( "argument resolved as {}", simpleUserDetail.getUsername() );
            return simpleUserDetail.getUser();
        }
    }

}
