package test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import test.dto.UserDto;
import test.util.DbcpBean;

public class UserDao {
	private static UserDao dao;
	
	// static 초기화 블럭 (이 클래스가 최초 사용될 때 한 번만 실행되는 블럭
	static{
		// static 초기화 작업을 여기서 한다(UserDao) 객체를 생성해서 static 필드에 다
		dao=new UserDao();
	}
	
	// 외부에서 UserDao 객체를 생성하지 못하도록 생성자를 private 로 막는다.
	private UserDao(){}
	
	// UserDao 객체의 참조값을 리턴해주는 public static 메소드 제공
	public static UserDao getInstance() {
		return dao;
	}
	
	/* ************************************************** */
	/* ************************************************** */
	// 이메일과 프로필을 수정하는 메소드
	public boolean updateEmailProfile(UserDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			String sql = """
					UPDATE users
					SET email=?, profileImage=?, updatedAt=SYSDATE
					WHERE userName =?
			""";
			pstmt = conn.prepareStatement(sql);
			// ? 에 순서대로 필요한 값 바인딩 
			// 예시 pstmt.setString(1, dto.getName());
			pstmt.setString(1, dto.getEmail());
			pstmt.setString(2, dto.getProfileImage());
			pstmt.setString(3, dto.getUserName());

			rowCount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					;
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (rowCount > 0) {
			return true; //
		} else {
			return false;
		}
	}

	/* ************************************************** */
	// 이메일을 수정하는 메소드
	public boolean updateEmail(UserDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			String sql = """
					UPDATE users
					SET EMAIL = ? 
					WHERE USERNAME = ?
			""";
			pstmt = conn.prepareStatement(sql);
			// ? 에 순서대로 필요한 값 바인딩 
			pstmt.setString(1, dto.getEmail());
			pstmt.setString(2, dto.getUserName());

			rowCount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					;
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (rowCount > 0) {
			return true; //
		} else {
			return false;
		}
	}
	
	/* ************************************************** */
	public boolean updatePassword(UserDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			String sql = """
					UPDATE USERS
					SET PASSWORD=?, updatedAt=SYSDATE
					WHERE userName=?
					""";
			pstmt = conn.prepareStatement(sql);
			// ? 에 순서대로 필요한 값 바인딩 
			// 예시 pstmt.setString(1, dto.getName());
			pstmt.setString(1, dto.getPassword());
			pstmt.setString(2, dto.getUserName());

			rowCount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					;
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (rowCount > 0) {
			return true; //
		} else {
			return false;
		}
	}

	
	/* ************************************************** */
	// userName 을 이용해서 회원 한 명의 정보를 리턴하는 메소드
	
	public UserDto getByUserName(String userName) {
		UserDto dto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = new DbcpBean().getConn();
			// 실행 할 sql 
			String sql = """
					SELECT num, password, email, profileImage, role, updatedAt, createdAt
					FROM users
					WHERE userName = ?
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			rs = pstmt.executeQuery();
			// 만일 select 되는 row 가 존재한다면
			if (rs.next()) { 
				dto=new UserDto();
				dto.setNum(rs.getLong("num"));
				dto.setUserName(userName);
				dto.setPassword(rs.getString("password"));
				dto.setEmail(rs.getString("email"));
				dto.setProfileImage(rs.getString("profileImage"));
				dto.setRole(rs.getString("role"));
				dto.setUpdatedAt(rs.getString("updatedAt"));
				dto.setCreatedAt(rs.getString("createdAt"));
				

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	
	/***********************************************/
	
	// 회원 정보 추가
	public boolean insert(UserDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			String sql = """
					INSERT INTO users
					(num, userName, password, email, updatedAt, createdAt)
					VALUES(users_seq.NEXTVAL, ?, ?, ?, SYSDATE, SYSDATE)
					""";
			pstmt = conn.prepareStatement(sql);
			// ? 에 들어갈 바인딩
			// 예시: pstmt.setString(1, dto.getName());
			pstmt.setString(1, dto.getUserName());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getEmail());

			rowCount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					;
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (rowCount > 0) {
			return true; //
		} else {
			return false;
		}
	}
	
	
}
