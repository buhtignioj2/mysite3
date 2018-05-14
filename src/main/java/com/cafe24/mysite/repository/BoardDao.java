package com.cafe24.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.BoardVo;

@Repository
public class BoardDao {

    @Autowired
    private SqlSession sqlSession;
    
    public List<BoardVo> getList(String keyword, int page, int size) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put( "keyword", keyword );
	map.put( "size", size );
	map.put( "startPage", ((page-1)*size) );
	
	return sqlSession.selectList( "board.getList", map );
    }

    public BoardVo get(long boardNo) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put( "no", boardNo );

	return sqlSession.selectOne( "board.getView", map );
    }

    public int insert(BoardVo vo) {
	return sqlSession.insert( "board.insert", vo );
    }

    public void insertd(BoardVo vo) {
	Connection conn = null;
	PreparedStatement pstmt = null;
	try {
	    conn = getConnection();
	    if ( vo.getGroupNo() == null ) {
		String sql = "insert "
			+ "  into board(no, title, content, group_no, order_no, depth, hit, reg_date, user_name, user_no)\r\n"
			+ " select null, ?, ?,  ifnull(max(group_no)+1, 1), 0, 0, 0, now(), ?, ? " + " from board ";
		pstmt = conn.prepareStatement( sql );

		pstmt.setString( 1, vo.getTitle() );
		pstmt.setString( 2, vo.getContent() );
		pstmt.setString( 3, vo.getUserName() );
		pstmt.setLong( 4, vo.getUserNo() );
	    } else {
		String sql = " insert" + "   into board" + " values( null, ?, ?, ?, ?, ?, 0, now(), ?, ? )";
		pstmt = conn.prepareStatement( sql );

		pstmt.setString( 1, vo.getTitle() );
		pstmt.setString( 2, vo.getContent() );
		pstmt.setInt( 3, vo.getGroupNo() );
		pstmt.setInt( 4, vo.getOrderNo() );
		pstmt.setInt( 5, vo.getDepth() );
		pstmt.setString( 6, vo.getUserName() );
		pstmt.setLong( 7, vo.getUserNo() );
	    }
	    System.out.println( vo );

	    pstmt.executeUpdate();

	} catch ( SQLException e ) {
	    System.out.println( "error:" + e );
	} finally {
	    try {
		if ( pstmt != null ) {
		    pstmt.close();
		}
		if ( conn != null ) {
		    conn.close();
		}
	    } catch ( SQLException e ) {
		System.out.println( "error:" + e );
	    }
	}
    }

    public boolean update(BoardVo vo) {
	boolean result = false;

	Connection conn = null;
	PreparedStatement pstmt = null;

	try {
	    conn = getConnection();
	    String sql = "update board set title = ?, content = ? where no =? and users_no = ?";
	    pstmt = conn.prepareStatement( sql );
	    pstmt.setString( 1, vo.getTitle() );
	    pstmt.setString( 2, vo.getContent() );
	    pstmt.setLong( 3, vo.getNo() );
	    pstmt.setLong( 4, vo.getUserNo() );

	    int count = pstmt.executeUpdate();
	    result = ( count == 1 );
	} catch ( Exception e ) {
	    e.printStackTrace();
	} finally {
	    try {
		if ( pstmt != null ) {
		    pstmt.close();
		}
		if ( conn != null ) {
		    conn.close();
		}
	    } catch ( SQLException e ) {
		e.printStackTrace();
	    }
	}

	return result;
    }

    
    public int delete( BoardVo boardVo ) {
	return sqlSession.delete( "board.delete", boardVo );
}

    public int getTotalCount(String keyword) {
	int totalCount = 0;

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;

	try {
	    conn = getConnection();
	    if ( "".equals( keyword ) ) {
		sql = "select count(*) from board";
		pstmt = conn.prepareStatement( sql );
	    } else {
		sql = "select count(*) from board where title like ? or content like ?";
		pstmt = conn.prepareStatement( sql );

		pstmt.setString( 1, "%" + keyword + "%" );
		pstmt.setString( 2, "%" + keyword + "%" );
	    }
	    rs = pstmt.executeQuery();
	    if ( rs.next() ) {
		totalCount = rs.getInt( 1 );
	    }

	} catch ( SQLException e ) {
	    e.printStackTrace();
	} finally {
	    try {
		if ( rs != null ) {
		    rs.close();
		}
		if ( pstmt != null ) {
		    pstmt.close();
		}
		if ( conn != null ) {
		    conn.close();
		}
	    } catch ( Exception e ) {
		e.printStackTrace();
	    }
	}

	return totalCount;
    }

    public void increaseGroupOrder(Integer groupNo, Integer orderNo) {
	Connection conn = null;
	PreparedStatement pstmt = null;

	try {
	    conn = getConnection();

	    String sql = "update board set order_no = order_no+1 where group_no = ? and order_no > ?";
	    pstmt = conn.prepareStatement( sql );

	    pstmt.setInt( 1, groupNo );
	    pstmt.setInt( 2, orderNo );

	    pstmt.executeUpdate();
	} catch ( SQLException e ) {
	    System.out.println( "error:" + e );
	} finally {
	    try {
		if ( pstmt != null ) {
		    pstmt.close();
		}
		if ( conn != null ) {
		    conn.close();
		}
	    } catch ( SQLException e ) {
		System.out.println( "error:" + e );
	    }
	}
    }

    private Connection getConnection() throws SQLException {
	Connection conn = null;
	try {
	    // 1. 드라이버 로딩
	    Class.forName( "com.mysql.jdbc.Driver" );

	    // 2. 연결하기
	    String url = "jdbc:mysql://localhost/webdb";
	    conn = DriverManager.getConnection( url, "webdb", "webdb" );
	} catch ( ClassNotFoundException e ) {
	    System.out.println( "드러이버 로딩 실패:" + e );
	}

	return conn;
    }

    public int updateHit( Long no ) {
	return sqlSession.update( "board.updateHit", no );
    }

}
