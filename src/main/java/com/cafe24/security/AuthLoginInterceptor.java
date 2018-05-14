package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVo;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	String email = request.getParameter( "email" );
	String password = request.getParameter( "password" );
	
	UserVo vo = new UserVo();
	vo.setEmail( email );
	vo.setPassword( password );
	
	//Spring Context 가져오기
	ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext( request.getServletContext() );
	
	//스프링 빈 가져오기
	UserService userService = ac.getBean( UserService.class );
	UserVo authUser = userService.getUser(vo);
	
	if( authUser == null ) {
	    response.sendRedirect( request.getContextPath() + "/user/login" );
	    return false;
	}
	
	HttpSession session = request.getSession( true );
	session.setAttribute( "authUser", authUser );
	response.sendRedirect( request.getContextPath() + "/main" );
	return false;
    }

}
