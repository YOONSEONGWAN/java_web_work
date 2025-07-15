package test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import test.dto.MemberDto;
import test.util.DbcpBean;

/*
 * 	Data Access Object
 * 
 * 	- DB 에 insert, update, delete 작업을 하는 코드를 작성한다. 
 */
public class MemberDao {
	
	// 회원 번호를 이용해서 회원 1명의 정보를 리턴하는 메소드
	public MemberDto getByNum(int num) {
		// MemberDto 객체의 참조값을 담을 지역번수를 미리 만든다.
		
		MemberDto dto=null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = new DbcpBean().getConn();
			// 실행 할 sql 
			String sql = """
					SELECT NAME, ADDR
					FROM MEMBER
					WHERE NUM = ?
					""";
			pstmt = conn.prepareStatement(sql);
			// ?? 에 값 바인딩
			pstmt.setInt(1, num);
			//	select 문 실행하고 결과를 ResultSet 으로 받아온다
			rs = pstmt.executeQuery();
			// 반복문 돌면서 ResultSet 에 담긴 데이터를 추출해서 어떤 객체에 담기
			while (rs.next()) {
				dto = new MemberDto();
				// 객체 생성 후 회원 한 명의 정보를 담는다
				dto.setNum(num);
				dto.setName(rs.getString("name"));
				dto.setAddr(rs.getString("addr"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 열린 순서 반대로 닫아야 함
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
	
	/* ************************************************** */
	
	// 회원 전체 목록을 SELECT 해서 List 에 담아 리턴하는 메소드
	public List<MemberDto> selectAll(){
		
		// 회원 정보를 누적시킬 ArrayList 객체 미리 준비하기 
		List<MemberDto> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = new DbcpBean().getConn();
			// 실행 할 sql 
			String sql = """
					SELECT NUM, NAME, ADDR
					FROM MEMBER
					ORDER BY NUM ASC
					""";
			pstmt = conn.prepareStatement(sql);
			// ?? 에 값 바인딩

			//	select 문 실행하고 결과를 ResultSet 으로 받아온다
			rs = pstmt.executeQuery();
			// 반복문 돌면서 ResultSet 에 담긴 데이터를 추출해서 어떤 객체에 담기
			while (rs.next()) {
				MemberDto dto=new MemberDto();
				dto.setNum(rs.getInt("num"));
				dto.setName(rs.getString("name"));
				dto.setAddr(rs.getString("addr"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 열린 순서 반대로 닫아야 함
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
		
		return list;
	}

	/* ************************************************** */
	
	// 회원 한 명의 정보를 DB 에서 수정하고 성공여부를 리턴하는 메소드 
	public boolean update(MemberDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		// 변화된 row 의 갯수를 담을 변수 선언하고 0으로 초기화
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			String sql = """
					UPDATE MEMBER
					SET NAME=?, ADDR=?
					WHERE NUM = ?
					""";
			pstmt = conn.prepareStatement(sql);
			//? 에 순서대로 필요한 값
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getAddr());
			pstmt.setInt(3, dto.getNum());
			
			
			// sql 문 실행하고 변화된(추가, 수정, 삭제) row 의 갯수 			리턴받기
			//만약 추가 되어서 1 row ~~ 라는 값을 리턴받으면 아래의 위치에 			1이 리턴되고 로우카운트를 알면 성공인지 실패인지 알 수 있다
			rowCount = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 널이 아닌 경우에만 메소드가 호출되어 종료하도록
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		if (rowCount > 0) {
			return true; // 작업 성공이라는 의미에서 true 값 리턴
		} else {
			return false; // 작업 실패라는 의미에서 false 값 리턴
		}
	}

	/* ************************************************** */
	
	// 회원 한 명의 정보를 DB 에서 삭제하고 성공여부를 리턴하는 메소드
	public boolean deleteByNum(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		// 변화된 row 의 갯수를 담을 변수 선언하고 0으로 초기화
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			String sql = """
					DELETE FROM MEMBER
					WHERE NUM = ?
					""";
			pstmt = conn.prepareStatement(sql);
			//? 에 순서대로 필요한 값
			pstmt.setInt(1, num);
			// sql 문 실행하고 변화된(추가, 수정, 삭제) row 의 갯수 			리턴받기
			//만약 추가 되어서 1 row ~~ 라는 값을 리턴받으면 아래의 위치에 			1이 리턴되고 로우카운트를 알면 성공인지 실패인지 알 수 있다
			rowCount = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 널이 아닌 경우에만 메소드가 호출되어 종료하도록
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		if (rowCount > 0) {
			return true; // 작업 성공이라는 의미에서 true 값 리턴
		} else {
			return false; // 작업 실패라는 의미에서 false 값 리턴
		}
	}
	

	/* ************************************************** */
	
	// 회원 한 명의 정보를 DB 에 저장하고 성공 여부를 리턴하는 메소드
	public boolean insert(MemberDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		// 변화된 row 의 갯수를 담을 변수 선언하고 0으로 초기화
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			String sql = """
					INSERT INTO MEMBER
					(NUM, NAME, ADDR)
					VALUES(MEMBER_SEQ.NEXTVAL, ?, ?)	
					""";
			pstmt = conn.prepareStatement(sql);
			//? 에 순서대로 필요한 값
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getAddr());
			// sql 문 실행하고 변화된(추가, 수정, 삭제) row 의 갯수 			리턴받기
			//만약 추가 되어서 1 row ~~ 라는 값을 리턴받으면 아래의 위치에 			1이 리턴되고 로우카운트를 알면 성공인지 실패인지 알 수 있다
			rowCount = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 널이 아닌 경우에만 메소드가 호출되어 종료하도록
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		if (rowCount > 0) {
			return true; // 작업 성공이라는 의미에서 true 값 리턴
		} else {
			return false; // 작업 실패라는 의미에서 false 값 리턴
		}
	}
}
