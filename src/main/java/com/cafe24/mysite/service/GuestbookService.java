package com.cafe24.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.GuestbookDao;
import com.cafe24.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {

    @Autowired
    private GuestbookDao guestbookDao;

    public List<GuestbookVo> getList() {
	return guestbookDao.getList();
    }

    public int insert(GuestbookVo vo) {
	return guestbookDao.insert( vo );
    }

    public GuestbookVo insert2(GuestbookVo guestbookVo) {
	GuestbookVo vo = null;
	
	int count = guestbookDao.insert(guestbookVo);
	if( count == 1 ) {
	    vo = guestbookDao.get( guestbookVo.getNo() );
	}
	
	return vo;
    }
    
    public boolean delete(GuestbookVo vo) {
	System.out.println( "######VO: " + vo );
	return guestbookDao.delete( vo ) == 1;
    }

    public List<GuestbookVo> getMessageList(Long no) {
	List<GuestbookVo> list = guestbookDao.getList(no);
	return list;
    }
    
    
}
