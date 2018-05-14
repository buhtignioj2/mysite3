package com.cafe24.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cafe24.mysite.dto.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Log LOG = LogFactory.getLog( GlobalExceptionHandler.class );
    
    @ExceptionHandler( Exception.class )
    public void handlerException( HttpServletRequest request, HttpServletResponse response, Exception e ) throws Exception{

	// 1. 로깅
	StringWriter errors = new StringWriter();
//	e.printStackTrace( new PrintWriter( errors ) );
	LOG.info( errors );
	
	String accept = request.getHeader( "accept" );
	if( accept.matches( ".*application/json.*" ) ) {
	    // 2. 실패 JSON 응답
	    JSONResult jsonResult = JSONResult.fail( errors.toString() );
	    
	    // 객체안의 내용을 {.....} , [{}, {}, {} ] 이둘중하나로 바꿔줌
	    String json = new ObjectMapper().writeValueAsString( jsonResult );
	    response.setContentType( "application/json; charset=utf-8" );
	    response.getWriter().print( json );
	    
	} else {
	    // 2. 사과 페이지
	    request.getRequestDispatcher( "/WEB-INF/views/error/exception.jsp" ).forward( request, response );
	}
	
    }
}
