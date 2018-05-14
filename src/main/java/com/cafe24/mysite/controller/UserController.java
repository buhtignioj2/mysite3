package com.cafe24.mysite.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVo;
import com.cafe24.security.Auth;
import com.cafe24.security.AuthUser;

@Controller
@RequestMapping( "/user" ) 
public class UserController {
   
    @Autowired
    private UserService userService;

    @RequestMapping( value="join", method=RequestMethod.GET )
    public String join( @ModelAttribute UserVo vo ) {
	return "user/join";
    }
    
    @RequestMapping( value="join", method=RequestMethod.POST )
    public String join( @ModelAttribute @Valid UserVo vo, BindingResult result ) {
	if( result.hasErrors() ) {
	    
	    List<ObjectError> list = result.getAllErrors();
	    for( ObjectError error : list ) {
		System.out.println( "Object Error: " + error );
	    }
	       
	    return "user/join";
	}
	
	userService.join( vo );
	return "redirect:/user/joinsuccess";
    }
    
    @RequestMapping( value="/joinsuccess" )
    public String joinsuccess() {
	return "user/joinsuccess";
    }
    
    @RequestMapping( value="/login", method=RequestMethod.GET )
    public String login() {
	return "user/login";
    }
    
    @Auth /*( role = Role.ADMIN )*/
    @RequestMapping( value="/modify", method=RequestMethod.GET )
    public String modify( @AuthUser UserVo authUser, Model model ) {
//	UserVo authUser = (UserVo)session.getAttribute( "authUser" );
	System.out.println( authUser );
	UserVo vo = userService.getUser( authUser );
	model.addAttribute( "vo", vo );
	
	
	if( authUser == null ) {
	    return "redirect:/main";
	}
	
	return "user/modify";
    }

    /*
    @ExceptionHandler( UserDaoException.class )
    public String handleUserDaoException() {
	 로그남기기 
    	return "error/exception.jsp";
    } 
    */

}
