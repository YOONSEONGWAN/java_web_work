package test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import test.dto.BoardDto;
import test.util.DbcpBean;

/*
 * 	1. 글을 쓰면 insert into 로 row 를 추가해야함
 * 	2. db 에서 삭제가 가능해야함 -> delete()
 * 	3. 수정도 가능해야함 -> update()
 * 	4. 사진도 수정이 가능해야함 
 */
public class BoardDao {
	private static BoardDao dao;
	
	// static 초기화 블럭 (이 클래스가 최초 사용될 때 한 번만 실행되는 블럭
	static{
		// static 초기화 작업을 여기서 한다(BoardDao) 객체를 생성해서 static 필드에 다
		dao=new BoardDao();
	}
	
	// 외부에서 UserDao 객체를 생성하지 못하도록 생성자를 private 로 막는다.
	private BoardDao(){}
	
	// UserDao 객체의 참조값을 리턴해주는 public static 메소드 제공
	public static BoardDao getInstance() {
		return dao;
	}

	/* ************************************************** */
	// 조회수를 증가시키는 메소드
	public boolean addViewCount(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			String sql = """
				UPDATE board
				SET viewCount = viewCount+1
				WHERE num=?
			""";
			pstmt = conn.prepareStatement(sql);
			// ? 에 순서대로 필요한 값 바인딩 
			// 예시 pstmt.setString(1, dto.getName());
			pstmt.setInt(1, num);

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
	// 전체 글의 갯수를 리턴하는 메소드
	public int getCount(){
	int count=0;
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
		conn = new DbcpBean().getConn();
		String sql = """
			SELECT MAX(ROWNUM) AS count
			FROM board
		""";
		pstmt = conn.prepareStatement(sql);
		
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			count=rs.getInt("count");
		}
	}catch(Exception e) {
		e.printStackTrace();
	}finally {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null);
			pstmt.close();
			if(conn!=null);
			conn.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	return count;
}

		
	/* ************************************************** */
	
	// 특정 page 에 해당하는 row 만 select 해서 리턴하는 메소드
	// BoardDto 객체에 startRowNum 과 endRowNum 을 담아와서 select
	public List<BoardDto> selectPage(BoardDto dto2){
		
		List<BoardDto> list=new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = new DbcpBean().getConn();
			String sql = """
				SELECT *
				FROM
					(SELECT result1.*, ROWNUM AS rnum
					FROM
						(SELECT num, writer, title, content, viewCount, createdAt
						FROM board
						ORDER BY num DESC) result1)
				WHERE rnum BETWEEN ? AND ?
			""";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto2.getStartRowNum());
			pstmt.setInt(2, dto2.getEndRowNum());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dto2=new BoardDto();
				dto2.setNum(rs.getInt("NUM"));
				dto2.setWriter(rs.getString("writer"));
				dto2.setTitle(rs.getString("title"));
				dto2.setViewCount(rs.getInt("viewCount"));
				dto2.setCreatedAt(rs.getString("createdAt"));
				list.add(dto2);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null);
				pstmt.close();
				if(conn!=null);
				conn.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/* ************************************************** */

	public boolean update(BoardDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			String sql = """
					UPDATE board
					SET title=?, content=?
					WHERE num=?
			""";
			pstmt = conn.prepareStatement(sql);
			// ? 에 순서대로 필요한 값 바인딩 
			// 예시 pstmt.setString(1, dto.getName());
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getNum());

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
	
	public boolean deleteByNum(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			String sql = """
					DELETE FROM board
					WHERE NUM = ?
					""";
			pstmt = conn.prepareStatement(sql);
			// 바인딩 작성 예시: pstmt.setInt(1, num);
			pstmt.setInt(1, num);
			rowCount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					;
				pstmt.close();
				if (conn != null)
					;
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (rowCount > 0) {
			return true;
		} else {
			return false;
		}
	}

/* ************************************************** */

	public List<BoardDto> selectAll(){
		
		List<BoardDto> list=new ArrayList<>();
		
		BoardDto dto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = new DbcpBean().getConn();
			String sql = """
					SELECT num, writer, title, viewCount, createdAt
					FROM board
					ORDER BY NUM DESC
			""";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dto=new BoardDto();
				dto.setNum(rs.getInt("NUM"));
				dto.setWriter(rs.getString("writer"));
				dto.setTitle(rs.getString("title"));
				dto.setViewCount(rs.getInt("viewCount"));
				dto.setCreatedAt(rs.getString("createdAt"));
				list.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null);
				pstmt.close();
				if(conn!=null);
				conn.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/* ************************************************** */	
	
	// 글 하나의 정보를 리턴하는 메소드
	public BoardDto getByNum(int num) {
		BoardDto dto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = new DbcpBean().getConn();
			// 실행 할 sql 
			String sql = """
				SELECT *
				FROM	
					(SELECT b.num, writer, title, content, viewCount, 
						TO_CHAR(b.createdAt, 'YY"년" MM"월" DD"일" HH24:MI') AS createdAt, 
						profileImage,
						LAG(b.num, 1, 0) OVER (ORDER BY b.num DESC) AS prevNum,
						LEAD(b.num, 1, 0) OVER (ORDER BY b.num DESC) AS nextNum
					FROM board b
					INNER JOIN users u ON b.writer = u.userName) 
				WHERE num=?
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) { 
				dto=new BoardDto();
				dto.setNum(num);
				dto.setWriter(rs.getString("writer"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setViewCount(rs.getInt("viewCount"));
				dto.setCreatedAt(rs.getString("createdAt"));
				dto.setProfileImage(rs.getString("profileImage"));
				dto.setPrevNum(rs.getInt("prevNum"));
				dto.setNextNum(rs.getInt("nextNum"));
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

	/* ************************************************** */

		/* ************************************************** */
		// 글 번호를 미리 select 해서 리턴해주는 메소드
		public int getSequence() {
			// 글 번호를 저장할 지역변수 미리 만들기
			int num=0;
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = new DbcpBean().getConn();
				String sql = """
						SELECT board_seq.NEXTVAL AS num FROM DUAL
							""";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					num=rs.getInt("num");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					if (rs != null)
						rs.close();
					if (pstmt != null);
					pstmt.close();
					if(conn!=null);
					conn.close();
					
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			return num;
}
		/* ************************************************** */
		public boolean insert(BoardDto dto) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			int rowCount = 0;
			try {
				conn = new DbcpBean().getConn();
				String sql = """
						INSERT INTO board
						(num, writer, title, content)
						VALUES(?, ?, ?, ?)
						""";
				pstmt = conn.prepareStatement(sql);
				// ? 에 들어갈 바인딩
				// 예시: pstmt.setString(1, dto.getName());
				pstmt.setInt(1, dto.getNum());
				pstmt.setString(2, dto.getWriter());
				pstmt.setString(3, dto.getTitle());
				pstmt.setString(4, dto.getContent());
				// sql 문 실행하고 변화된(추가된, 수정된, 삭제된) row 의 갯수 리턴받기
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
