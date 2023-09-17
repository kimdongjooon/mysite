package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.GuestBookVo;

@Repository
public class GuestBookRepository {
	// 입력 게시글 insert
	public boolean insert(GuestBookVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

		// Timestamp to String
		String currentTimestampToString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTimestamp);
		
		
		try {

			conn = getConnection();

			// 3. sql준비.

			String sql = "insert into guestbook values(null,?,?,?,?);";

			pstmt = conn.prepareStatement(sql);

			// 4. 값 바인딩.
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContents());
			pstmt.setString(4, currentTimestampToString);

			// 5. SQL 실행.
			int count = pstmt.executeUpdate();

			// 6. 결과 처리.
			result = count == 1;

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

	// 게시글 모든글 출력 findAll - 리스트반환.
	public List<GuestBookVo> findAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<GuestBookVo> result= new ArrayList<>();
		
		try {
			conn = getConnection();

			//3. SQL 준비
			String sql =
				"select * from guestbook";
			   
			pstmt = conn.prepareStatement(sql);
			
			//4. binding
			
			
			//5. SQL 실행
			rs = pstmt.executeQuery();
			
			//6. 결과 처리
			while(rs.next()) {
				int no = rs.getInt(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String content = rs.getString(4);
				String date = rs.getString(5);
				
				GuestBookVo vo = new GuestBookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setPassword(password);
				vo.setContents(content);
				vo.setDate(date);
				
				result.add(vo);
				
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
	// no 로 접근하여 특정 게시글 삭제 delete
	public void deleteById(int no, String password) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			conn = getConnection();

			//3. SQL 준비
			String sql =
				"delete from guestbook " +
				" where no = ? "+
				" and password = ?"		;
			   
			pstmt = conn.prepareStatement(sql);
			
			//4. binding
			pstmt.setInt(1,no);
			pstmt.setString(2,password);
			
			//5. SQL 실행
			rs = pstmt.executeQuery();

			
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
		
		

		
	}
	
	
	
	
	private Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.1.18:3307/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return conn;

	}

}
