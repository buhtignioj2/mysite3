package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.cafe24.mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
	if( supportsParameter( parameter ) == false ) {
	    return WebArgumentResolver.UNRESOLVED;
	}
	
	// @AuthUser가 붙어 있고 파라미터 타입이 UserVo
	HttpServletRequest request = webRequest.getNativeRequest( HttpServletRequest.class );
	HttpSession session = request.getSession();
	
	if( session == null ) {
	    return null;
	}
	
	return session.getAttribute( "authUser" );
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
	
	//1. 파라미터에 @AuthUser가 붙어있는지 확인
	AuthUser authUser = parameter.getParameterAnnotation( AuthUser.class );
	
	//2. @AuthUser가 안붙어 있음
	if( authUser == null ) {
	    return false;
	}
	
	//3. @AuthUser가 붙어있지만 Type이 UserVo가 맞는지 확인
	if( parameter.getParameterType().equals( UserVo.class ) == false ) {
	    return false;
	}

	return true;
    }

}
