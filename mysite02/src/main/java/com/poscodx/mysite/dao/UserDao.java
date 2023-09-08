package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.poscodx.mysite.vo.UserVo;

public class UserDao {

	public boolean insert(UserVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

//		// Timestamp to String
//		String currentTimestampToString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTimestamp);
		
		
		try {

			conn = getConnection();

			// 3. sql준비.

			String sql = 
					"insert "+
					"into user "+
					"values (null,?,?,password(?),?, current_date())";

			pstmt = conn.prepareStatement(sql);

			// 4. 값 바인딩.
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

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
