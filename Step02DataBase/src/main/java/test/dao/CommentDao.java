package test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import test.dto.CommentDto;
import test.util.DbcpBean;

public class CommentDao {
	private static CommentDao dao;
	static {
		dao=new CommentDao();
	}
	// 생성자를 private 로 해서 외부에서 객체 생성하지 못하도록 (싱글톤패턴)
	private CommentDao() {} // 이걸로 객체 못 만듦
	// 자신의 참조값을 리턴해주는 static 메소드 제공
	public static CommentDao getInstance() {
		return dao;
	}

	/* ************************************************** */
	// 댓글을 삭제하는 메소드 DB에서 지우면 안 되니까 update 문을 사용한다
	public boolean delete(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			String sql = """
				UPDATE comments
				SET DELETED='YES'
				WHERE NUM=?
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
	// 댓글을 수정하는 메소드
	
	public boolean update(CommentDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			String sql = """
				UPDATE comments
				SET content=?
				WHERE num=?
			""";
			pstmt = conn.prepareStatement(sql);
			// ? 에 순서대로 필요한 값 바인딩 
			// 예시 pstmt.setString(1, dto.getName());
			pstmt.setString(1, dto.getContent());
			pstmt.setInt(2, dto.getNum());

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
	// 원글(parentNum) 에 달린 모든 댓글을 리턴하는 메소드
	
	public List<CommentDto> selectList(int parentNum){
		
		List<CommentDto> list=new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = new DbcpBean().getConn();
			String sql = """
				SELECT comments.num, writer, targetWriter, content, deleted, groupNum, 
						comments.createdAt, profileImage
				FROM comments
				INNER JOIN users ON comments.writer = users.userName 
				WHERE parentNum=? 
				ORDER BY groupNum ASC, num ASC
			""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, parentNum);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CommentDto dto=new CommentDto();
				dto.setNum(rs.getInt("num"));
				dto.setWriter(rs.getString("writer"));
				dto.setTargetWriter(rs.getString("targetWriter"));
				dto.setContent(rs.getString("content"));
				dto.setParentNum(parentNum);
				dto.setGroupNum(rs.getInt("groupNum"));
				dto.setDeleted(rs.getString("deleted"));
				dto.setCreatedAt(rs.getString("createdAt"));
				dto.setProfileImage(rs.getString("profileImage"));
				
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
	// 댓글 정보를 DB 에 저장하는 메소드 update 문
	public boolean insert(CommentDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			String sql = """
				INSERT INTO comments
				(num, writer, targetWriter, content, parentNum, groupNum)
				VALUES(?, ?, ?, ?, ?, ?)
			""";
			pstmt = conn.prepareStatement(sql);
			// ? 에 순서대로 필요한 값 바인딩 
			// 예시 pstmt.setString(1, dto.getName());
			pstmt.setInt(1, dto.getNum());
			pstmt.setString(2, dto.getWriter());
			pstmt.setString(3, dto.getTargetWriter());
			pstmt.setString(4, dto.getContent());
			pstmt.setInt(5, dto.getParentNum());
			pstmt.setInt(6, dto.getGroupNum());
			
			// sql 문 실행하고 변화된 row 개수 리턴받기

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
	// 저장할 댓글의 글번호를 리턴해주는 메소드
	public int getSequence(){
		int num=0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = new DbcpBean().getConn();
			String sql = """
				SELECT comments_seq.NEXTVAL AS num FROM DUAL
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
	
		
		
	
	
	
}
