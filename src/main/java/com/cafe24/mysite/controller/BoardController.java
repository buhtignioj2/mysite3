package com.cafe24.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.service.BoardService;
import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @RequestMapping("")
    public String list(@RequestParam(value = "p", required = true, defaultValue = "1") Integer page,
	    @RequestParam(value = "keyword", required = true, defaultValue = "") String keyword, Model model) {

	Map<String, Object> map = boardService.getList( keyword, page );
	model.addAttribute( "map", map );

	System.out.println( map );
	return "board/list";
    }

    @RequestMapping("/{no}/{page}")
    public String view(@PathVariable("no") Long no, @PathVariable("page") Integer page,
	    @RequestParam(value = "keyword", required = true, defaultValue = "") String keyword, Model model) {

	BoardVo vo = boardService.getView( no );
	model.addAttribute( "vo", vo );

	return "board/view";
    }

    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String write() {
	return "board/write";
    }

    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public String write(@ModelAttribute BoardVo vo,
	    @RequestParam(value = "p", required = true, defaultValue = "1") Integer page,
	    @RequestParam(value = "keyword", required = true, defaultValue = "") String keyword, HttpSession session,
	    Model model) {

	UserVo authUser = ( UserVo ) session.getAttribute( "authUser" );
	if ( authUser != null ) {
	    vo.setUserNo( authUser.getNo() );
	    vo.setUserName( authUser.getName() );
	    boardService.insert( vo );
	} 
	System.out.println( keyword );

	return "redirect:/board?p=" + page + "&keyword=" + keyword;
    }
    
    @RequestMapping( "/delete" )
    public String delete(@ModelAttribute BoardVo vo,
		@RequestParam( value="p", required=true, defaultValue="1") Integer page,
		@RequestParam( value="keyword", required=true, defaultValue="") String keyword, HttpSession session) {
	UserVo authUser = ( UserVo ) session.getAttribute( "authUser" );
	vo.setUserNo( authUser.getNo() );
	boardService.delete( vo );
	
	return "redirect:/board?p=" + page + "&keyword=" + keyword;
    }
    
    
	@RequestMapping( value="/reply", method=RequestMethod.GET )
	public String reply(
		@RequestParam( value="no", required=true, defaultValue="0") Long no,
		@RequestParam( value="p", required=true, defaultValue="1") Integer page,
		@RequestParam( value="keyword", required=true, defaultValue="") String keyword,
		Model model ){
			
		BoardVo vo = boardService.getView(no);
			
		model.addAttribute( "vo", vo );
		model.addAttribute( "page", page );
		model.addAttribute( "keyword", keyword );

		return "board/reply";
	}
}
