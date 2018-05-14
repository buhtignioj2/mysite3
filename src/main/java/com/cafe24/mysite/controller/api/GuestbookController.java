package com.cafe24.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.mysite.dto.JSONResult;
import com.cafe24.mysite.service.GuestbookService;
import com.cafe24.mysite.vo.GuestbookVo;



@Controller( "guestbookAPIController" )
@RequestMapping( "/api/guestbook" )
public class GuestbookController {
    
//    @Autowired
//    private GuestbookDao guestbookDao;

    @Autowired
    private GuestbookService guestbookService;
    
//    @ResponseBody
//    @RequestMapping( "/list" )
//    public JSONResult list() {
//	List<GuestbookVo> list = guestbookDao.getList();
//	return JSONResult.success( list );
//    }
    
    @ResponseBody
    @RequestMapping( "/list" )
    public JSONResult list(@RequestParam(value="", required=true, defaultValue="0") Long no) {
	List<GuestbookVo> list = guestbookService.getMessageList(no);
	
	return JSONResult.success( list );
    }
    
    @ResponseBody
    @RequestMapping( "/insert" )
    public JSONResult insert( @RequestBody GuestbookVo vo ) {
	GuestbookVo guestbookVo = guestbookService.insert2( vo );
	return JSONResult.success( guestbookVo );
    }
    
    @ResponseBody
    @RequestMapping( "/delete" )
    public JSONResult delete( @ModelAttribute GuestbookVo vo) {
	boolean result = guestbookService.delete(vo);
	
	return JSONResult.success( result ? vo.getNo() : -1 );
    }
}
