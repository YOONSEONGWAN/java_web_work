package test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import test.dto.BookDto;
import test.util.DbcpBean;

public class BookDao {
	/* ************************************************** */
	public BookDto getByNum(int num) {
		BookDto dto=null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = new DbcpBean().getConn();
			// 실행 할 sql 
			String sql = """
					SELECT NAME, AUTHOR, PUBLISHER
					FROM BOOK
					WHERE NUM = ?
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			// 만일 select 된 row 가 있다면
			if (rs.next()) {
				// bookDto 객체를 생성해서 책 정보를 담는다.
				dto = new BookDto();
				dto.setNum(num);
				dto.setName(rs.getString("name"));
				dto.setAuthor(rs.getString("author"));
				dto.setPublisher(rs.getString("publisher"));
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

	public List<BookDto> selectAll(){
		
		List<BookDto> list=new ArrayList<BookDto>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = new DbcpBean().getConn();
			String sql = """
					SELECT NUM, NAME, AUTHOR, PUBLISHER
					FROM BOOK
					ORDER BY NUM DESC
					""";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BookDto dto=new BookDto();
				dto.setNum(rs.getInt("num"));
				dto.setName(rs.getString("name"));
				dto.setAuthor(rs.getString("author"));
				dto.setPublisher(rs.getString("publisher"));
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
	public boolean update(BookDto dto) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int rowCount=0;
		try {
			conn = new DbcpBean().getConn();
			String sql = """
					UPDATE BOOK
					SET NAME=?, AUTHOR=?, PUBLISHER=?
					WHERE NUM=?
					""";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getAuthor());
			pstmt.setString(3, dto.getPublisher());
			pstmt.setInt(4, dto.getNum());
			
			rowCount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt !=null)
					pstmt.close();
				if(conn !=null);
					conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		if (rowCount > 0) {
			return true; //
		}else {
			return false;
		}
	}
	
	
	/* ************************************************** */
	public boolean deleteByNum(int num) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		int rowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			String sql = """
					DELETE FROM BOOK
					WHERE NUM = ?
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rowCount = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (pstmt != null);
				pstmt.close();
				if(conn!=null);
				conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		if(rowCount>0) {
			return true;
		}else {
			return false;
		}
	}
	
	/* ************************************************** */
	public boolean insert(BookDto dto) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int rowCount=0;
		try {
			conn = new DbcpBean().getConn();
			String sql = """
					INSERT INTO BOOK
					(NUM, NAME, AUTHOR, PUBLISHER)
					VALUES(BOOK_SEQ.NEXTVAL, ?, ?, ?)
					""";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getAuthor());
			pstmt.setString(3, dto.getPublisher());
			
			rowCount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt !=null)
					pstmt.close();
				if(conn !=null);
					conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		if (rowCount > 0) {
			return true; //
		}else {
			return false;
		}
	}
}
