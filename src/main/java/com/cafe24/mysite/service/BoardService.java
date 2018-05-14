package com.cafe24.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.BoardDao;
import com.cafe24.mysite.vo.BoardVo;

@Service
public class BoardService {
    @Autowired
    BoardDao boardDao;

    private static final int LIST_SIZE = 5;
    private static final int PAGE_SIZE = 5;

    public Map<String, Object> getList(String keyword, int currentPage) {

	int totalCount = boardDao.getTotalCount( keyword );
	int pageCount = ( int ) Math.ceil( ( double ) totalCount / LIST_SIZE );
	int blockCount = ( int ) Math.ceil( ( double ) pageCount / PAGE_SIZE );
	int currentBlock = ( int ) Math.ceil( ( double ) currentPage / PAGE_SIZE );

	int beginPage = ( currentBlock == 0 ) ? 1 : ( currentBlock - 1 ) * PAGE_SIZE + 1;
	int prevPage = ( currentBlock > 1 ) ? ( currentBlock - 1 ) * PAGE_SIZE : 0;
	int nextPage = ( currentBlock < blockCount ) ? currentBlock * PAGE_SIZE + 1 : 0;
	int endPage = ( nextPage > 0 ) ? ( beginPage - 1 ) + LIST_SIZE : pageCount;

	List<BoardVo> list = boardDao.getList( keyword, currentPage, LIST_SIZE );

	Map<String, Object> map = new HashMap<String, Object>();
	map.put( "list", list );
	map.put( "totalCount", totalCount );
	map.put( "listSize", LIST_SIZE );
	map.put( "currentPage", currentPage );
	map.put( "beginPage", beginPage );
	map.put( "endPage", endPage );
	map.put( "prevPage", prevPage );
	map.put( "nextPage", nextPage );
	map.put( "keyword", keyword );

	return map;
    }

    public BoardVo getView(Long boardNo) {

	BoardVo vo = boardDao.get( boardNo );
	if ( vo != null ) {
	    boardDao.updateHit( boardNo );
	}
	return vo;
    }

    public boolean insert(BoardVo vo) {
	return boardDao.insert( vo ) == 1;
    }

    public boolean delete(BoardVo vo) {
	return boardDao.delete( vo ) == 1;
    }
}
