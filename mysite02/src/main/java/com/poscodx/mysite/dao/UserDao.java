package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.poscodx.mysite.vo.UserVo;

public class UserDao {
	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo userVo = null;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {

			conn = getConnection();

			// 3. sql준비.

			String sql = 
					"select no ,name, email "+ 
					"from user "+
					"where email = ? "+
					"and password=password(?)";

			pstmt = conn.prepareStatement(sql);

			// 4. 값 바인딩.
			pstmt.setString(1, email);
			pstmt.setString(2, password);

			// 5. SQL 실행.
			rs = pstmt.executeQuery();

			// 6. 결과 처리.
			if(rs.next()) {
				Long no = rs.getLong(1);
				String userName = rs.getString(2);
				String userEmail = rs.getString(3);
				
				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setName(userName);
				userVo.setEmail(userEmail);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				// 6. 자원정리
				if(rs != null) {
					rs.close();
				}
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

		return userVo;
	}
	public boolean insert(UserVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
//		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

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
	public String updateformSetGender(UserVo authUser) {
		String result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		
		try {

			conn = getConnection();

			// 3. sql준비.
			String sql = 
					"select gender "+
					"from user "+
					"where no = ?";

			pstmt = conn.prepareStatement(sql);

			// 4. 값 바인딩.
			pstmt.setLong(1, authUser.getNo());
			
			
			// 5. SQL 실행.
			rs = pstmt.executeQuery();

			// 6. 결과 처리.
			if(rs.next()) {
				
				result = rs.getString(1);
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
	
	
	
	public boolean updateUser(Long no, String name, String password, String gender) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;		
		
		try {

			conn = getConnection();

			// 3. sql준비.
			if(password == "") {
				String sql = 
						"update user "+
						"set name = ?, gender=? where no = ?";
	
				pstmt = conn.prepareStatement(sql);
	
				// 4. 값 바인딩.
				pstmt.setString(1, name);
				pstmt.setString(2, gender);
				pstmt.setLong(3, no);
			}else {
				String sql = 
						"update user "+
						"set name = ?, gender=?,password=password(?) where no = ?";
	
				pstmt = conn.prepareStatement(sql);
	
				// 4. 값 바인딩.
				pstmt.setString(1, name);
				pstmt.setString(2, gender);
				pstmt.setString(3, password);
				pstmt.setLong(4, no);
			}
			
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

			String address = "172.20.10.4:3307/webdb";
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
