package com.cafe24.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {
    @Autowired
    private SqlSession sqlSession;

    /*
     * public boolean delete(GuestbookVo vo) { Map<String, Object> map = new
     * HashMap<String, Object>();
     * 
     * map.put( "n", vo.getNo() ); map.put( "pwd", vo.getPassword() );
     * 
     * return sqlSession.delete( "guestbook.delete", map ) == 1; }
     */
    
    public int delete(GuestbookVo vo) {
	int count = sqlSession.delete( "guestbook.delete", vo );
	return count;
    }

    public int insert(GuestbookVo vo) {
	int count = sqlSession.insert( "guestbook.insert", vo );

	return count;
    }

    public List<GuestbookVo> getList() {
	List<GuestbookVo> list = sqlSession.selectList( "guestbook.getList" );
	return list;
    }

    public List<GuestbookVo> getList(Long no) {
	List<GuestbookVo> list = sqlSession.selectList( "guestbook.getList2", no );
	return list;
    }

    public GuestbookVo get(Long no) {
	return sqlSession.selectOne( "guestbook.getByNo", no );
    }
}
