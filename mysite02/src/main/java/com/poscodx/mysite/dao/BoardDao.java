package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.poscodx.mysite.vo.BoardListVo;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.GuestBookVo;
import com.poscodx.mysite.vo.UserVo;

public class BoardDao {
	
	// 게시물 입력 (insert)
	// 입력 데이터 (게시글 번호, 제목, 내용, 조회수, 입력날짜, 그룹넘버, 그룹안순서, 댓글깊이, 유저번호)
	//          (null, title, contents, hit, reg_date, g_no, o_no, depth, user_no)
	public void insert(BoardVo boardvo) {
		// 유저의 세션 authUser에서 user_no가져오기.
		// 그외에는 하나씩 구현.
//		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			// 1. webdb 연결 
			conn = getConnection();

			// 3. sql준비.

			String sql = 
					"insert "+
					"into board "+
					"values (null,?,?,?,?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);

			// 4. 값 바인딩.
			pstmt.setString(1, boardvo.getTitle());
			pstmt.setString(2, boardvo.getContents());
			pstmt.setInt(3, boardvo.getHit());
			pstmt.setString(4, boardvo.getReg_date());
			pstmt.setInt(5, boardvo.getG_no());
			pstmt.setInt(6, boardvo.getO_no());
			pstmt.setInt(7, boardvo.getDepth());
			pstmt.setLong(8, boardvo.getUser_no());

			// 5. SQL 실행.
			int count = pstmt.executeUpdate();

			// 6. 결과 처리.
//			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				// 6. 자원정리
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

//		return result;
		
	}
	
	// g_no 가져오기(select)
	public int setMaxG_no(UserVo authUser) {
		int result = 0; // 검색되는 데이터가 없으면 0반환. 새글이라는 의미.
		Connection conn = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		
		try {

			conn = getConnection();

			// 3. sql준비.
			String sql = 
					"select max(g_no) from board where user_no= ?";

			pstmt = conn.prepareStatement(sql);

			// 4. 값 바인딩.
			pstmt.setLong(1, authUser.getNo());
			
			
			// 5. SQL 실행.
			rs = pstmt.executeQuery();

			// 6. 결과 처리.
			if(rs.next()) { // 데이터가 존재하면 가장큰수 반환.
				result = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				// 6. 자원정리
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
			return result;
		
	}
	
	
	
	// 게시물 리스트 (select) 리스트형식으로 리턴.
	public List<BoardVo> boardListFindAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVo> result= new ArrayList<>();
		
		try {
			conn = getConnection();

			//3. SQL 준비
			String sql =
				"select a.no, " +
				"	    a.title, "+
				"       a.contents, "+
				"       a.hit, a.reg_date, "+
				"       a.g_no, "+
				"       a.o_no, "+
				"       a.depth, "+
				"       a.user_no, "+
				"       b.name  "+
				"from board a, user b "+
				"where a.user_no = b.no "+
				"order by g_no DESC, o_no ASC";
			   
			pstmt = conn.prepareStatement(sql);
			
			//4. binding
			
			//5. SQL 실행
			rs = pstmt.executeQuery();
			
			//6. 결과 처리
			while(rs.next()) {
				int no = rs.getInt(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String reg_date = rs.getString(5);
				int g_no = rs.getInt(6);
				int o_no = rs.getInt(7);
				int depth = rs.getInt(8);
				Long user_no = rs.getLong(9);
				String name = rs.getString(10);
				
				BoardVo boardvo = new BoardVo();
				boardvo.setNo(no);
				boardvo.setTitle(title);
				boardvo.setContents(contents);
				boardvo.setHit(hit);
				boardvo.setReg_date(reg_date);
				boardvo.setG_no(g_no);
				boardvo.setO_no(o_no);
				boardvo.setDepth(depth);
				boardvo.setUser_no(user_no);
				boardvo.setName(name);
				
				result.add(boardvo);
				
			}
			
		}  catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				// 6. 자원정리
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		
		return result;
		
	}
	
	// 게시글 검색.
	public BoardVo boardFindByNo(int no) {
		BoardVo boardvo = null; // 검색되는 데이터가 없으면 0반환. 새글이라는 의미.
		Connection conn = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		
		try {

			conn = getConnection();

			// 3. sql준비.
			String sql = 
					"select "+ 
					"	 title, "+
					"    contents, "+
					"    hit, "+
					"    reg_date,  "+
					"    g_no,  "+
					"    o_no,  "+
					"    depth "+
					"from board  "+
					"where no = ?";

			pstmt = conn.prepareStatement(sql);

			// 4. 값 바인딩.
			pstmt.setLong(1, no);
			
			
			// 5. SQL 실행.
			rs = pstmt.executeQuery();

			// 6. 결과 처리.
			if(rs.next()) { // 데이터가 존재하면 가장큰수 반환.
				String title = rs.getString(1);
				String contents = rs.getString(2);
				int hit = rs.getInt(3);
				String reg_date = rs.getString(4);
				int g_no = rs.getInt(5);
				int o_no = rs.getInt(6);
				int depth = rs.getInt(7);
				
				boardvo = new BoardVo();
				boardvo.setNo(no);
				boardvo.setTitle(title);
				boardvo.setContents(contents);
				boardvo.setHit(hit);
				boardvo.setReg_date(reg_date);
				boardvo.setG_no(g_no);
				boardvo.setO_no(o_no);
				boardvo.setDepth(depth);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				// 6. 자원정리
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return boardvo;
		
	}
	
	public void TitleContentUpdate(BoardVo boardvo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			// 1. webdb 연결 
			conn = getConnection();

			// 3. sql준비.

			String sql = 
					"update board "+
					"set  "+
					"	title = ?, "+
					"   contents = ? "+
					"where no = ?;";

			pstmt = conn.prepareStatement(sql);

			// 4. 값 바인딩.
			pstmt.setString(1, boardvo.getTitle());
			pstmt.setString(2, boardvo.getContents());		
			pstmt.setLong(3, boardvo.getUser_no());

			// 5. SQL 실행.
			int count = pstmt.executeUpdate();

			// 6. 결과 처리.
//			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				// 6. 자원정리
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	// max o_no 찾기 select
	public int maxO_NoByG_NoAndUserNo(int g_No, UserVo authUser) {
		int result=0; // 검색되는 데이터가 없으면 예외처리해야됨. 
		Connection conn = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		
		try {

			conn = getConnection();

			// 3. sql준비.
			String sql = 
					"select max(o_no) "+
					"from board "+
					"where g_no = ? "+
					"and user_no = ?";

			pstmt = conn.prepareStatement(sql);

			// 4. 값 바인딩.
			pstmt.setLong(1, g_No);
			pstmt.setLong(2, authUser.getNo());
			
			
			// 5. SQL 실행.
			rs = pstmt.executeQuery();

			// 6. 결과 처리.
			if(rs.next()) { // 데이터가 존재하면 가장큰수 반환.
				result = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				// 6. 자원정리
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
		
	}
	
	// 게시물 삭제 (delete)
	
	// 게시물 보기 (select)
	
	// 게시물 수정 (update)
	
	
	
	
	
	
	
	
	
	private Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.64.3:3307/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return conn;
	}

	

	

	

	

	
}
