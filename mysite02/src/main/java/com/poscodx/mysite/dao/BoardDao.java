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
import com.poscodx.mysite.vo.PageVo;
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
					"select max(g_no) from board";
//					"select max(g_no) from board where user_no= ?";

			pstmt = conn.prepareStatement(sql);

			// 4. 값 바인딩.
//			pstmt.setLong(1, authUser.getNo());
			
			
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
	public List<BoardVo> boardListFindFiveBoard(String kwd, int page) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVo> result= new ArrayList<>();
		page = (page-1)*5;
//		System.out.println("kwd:"+kwd);
//		System.out.println("page:"+page);
		if(kwd == null) {
			kwd="";
		}
//		System.out.println("kwd:"+kwd);
//		System.out.println("page:"+page);
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
					"and a.title like ? "+
					"order by g_no DESC, o_no ASC "+
					"limit ?,5 ";
			pstmt = conn.prepareStatement(sql);
			//4. binding
			pstmt.setString(1, "%"+kwd+"%");
			pstmt.setInt(2, page);
					
			
//				sql =
//						"select a.no, " +
//						"	    a.title, "+
//						"       a.contents, "+
//						"       a.hit, a.reg_date, "+
//						"       a.g_no, "+
//						"       a.o_no, "+
//						"       a.depth, "+
//						"       a.user_no, "+
//						"       b.name  "+
//						"from board a, user b "+
//						"where a.user_no = b.no "+
//						"and a.title like '%?%' "+
//						"order by g_no DESC, o_no ASC "+
//						"limit ?,5 ";
//				pstmt = conn.prepareStatement(sql);
//				//4. binding
//				pstmt.setString(1, kwd);
//				pstmt.setInt(2, page);
		

			
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
	
	// 게시물 보기 (select)
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
					"    depth, "+
					"    user_no "+
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
				Long user_no = rs.getLong(8);
				
				boardvo = new BoardVo();
				boardvo.setNo(no);
				boardvo.setTitle(title);
				boardvo.setContents(contents);
				boardvo.setHit(hit);
				boardvo.setReg_date(reg_date);
				boardvo.setG_no(g_no);
				boardvo.setO_no(o_no);
				boardvo.setDepth(depth);
				boardvo.setUser_no(user_no);
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
	
	// 게시물 수정 (update)
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
	
	
	public void updateO_NoByG_NoAndO_No(int g_No, int o_No) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			// 1. webdb 연결 
			conn = getConnection();

			// 3. sql준비.

			String sql = 
					"update board "+
					"set o_no = o_no+? "+
					"where g_no = ? "+
					"and o_no >= ? ";

			pstmt = conn.prepareStatement(sql);

			// 4. 값 바인딩.
			pstmt.setInt(1, 1);
			pstmt.setInt(2, g_No);
			pstmt.setInt(3, o_No);		
			

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
	
	// 게시물 삭제 (delete)
	public void deleteBoardByNo(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			// 1. webdb 연결 
			conn = getConnection();

			// 3. sql준비.

			String sql = 
					"delete from board "+
					"where no =? ";

			pstmt = conn.prepareStatement(sql);

			// 4. 값 바인딩.
			pstmt.setInt(1, no);	
			

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
	
	// 조회수 올리기
	public void updateHitByNo(int no, int hit) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			// 1. webdb 연결 
			conn = getConnection();

			// 3. sql준비.

			String sql = 
					"update board "+
					"set hit = hit+? "+
					"where no =? ";

			pstmt = conn.prepareStatement(sql);

			// 4. 값 바인딩.
			pstmt.setInt(1, hit);
			pstmt.setInt(2, no);
			

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
	
	
	// 보드 페이징 처리
	public PageVo boardPageSet(String kwd,int page) {
		Connection conn = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		PageVo pagevo = new PageVo(page);
		int startPage;
		int endPage;
		int currentPage = page;
		int nextPage;
		int prevPage;
		int totalPage=0;
		int totalBoard=0;
		int outputpage = 5;
		
		if(kwd == null) {
			kwd="";
		}
		try {

			conn = getConnection();

			// 3. sql준비.
			String sql = 
					"select count(*) from board where title like ?";
//			 
			pstmt = conn.prepareStatement(sql);

			// 4. 값 바인딩.
			pstmt.setString(1,"%"+kwd+"%");
			
			// 5. SQL 실행.
			rs = pstmt.executeQuery();

			// 6. 결과 처리.
			if(rs.next()) { // totalpage 삽입.
				totalBoard = rs.getInt(1);
				if(totalBoard % outputpage !=0) {
					totalPage = totalBoard/outputpage +1;
				}else if(totalBoard==0){
					totalPage = 1;
				}else {
					totalPage = totalBoard/outputpage ;
				}
			}
			
			//startPage : currentPage -2
			// 예외1 : currentPage -2 <= 0 일때 1삽입.
			if((currentPage-2)<=0) {
				startPage = 1;
			}else {
				startPage = currentPage-2;
			}
			// 예외2. currentpage가 totalPage , totalPage-1 일때
			// 여기는 totalPage가 5초과일때. StartPage = totalPage-5 삽입.
			if(currentPage==totalPage || currentPage==totalPage-1) {
				if(totalPage>5) {
					startPage = totalPage-4 ;
				}
				else {
					startPage = 1;
				}
			}
			
			
			
			// endPage : currentPage +2
			// 예외1. currentPage+2 > totalpage 일경우 endpage에 totalpage삽입.
			if((currentPage+2) > totalPage) {
				endPage = totalPage;
			}else {
				endPage = currentPage+2;
			}
			// 예외2. currentpage가 1또는 2일때 총페이지가 5보다 클때.
			if(currentPage==1 || currentPage==2) {
				if(totalPage>5) {
					endPage = outputpage;
				}
				else {
					endPage = totalPage;
				}
			}

			
			
			pagevo.setStartPage(startPage);
			pagevo.setEndPage(endPage);
			pagevo.setNextPage(currentPage+1);
			pagevo.setPrevPage(currentPage-1);
			pagevo.setTotalPage(totalPage);
			pagevo.setTotalBoard(totalBoard);
			
			
			
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
		return pagevo;
	}
	
	
	
	
	
	
	private Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
//			192.168.64.3:3307/webdb
			String address = "192.168.0.204:3307/webdb";
			String url = "jdbc:mariadb://"+address+"?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return conn;
	}

	

	

	

	

	

	

	

	

	
}
