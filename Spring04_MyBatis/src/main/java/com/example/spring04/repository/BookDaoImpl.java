package com.example.spring04.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.spring04.dto.BookDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao{
	// private 니까 생성자 필요. 
	private final SqlSession session;
	
	/* 그러나 @RequiredArgsConstructor 이 메소드 사용하여 생략이 가능
	public BookDaoImpl(SqlSession session) {
		this.session=session;
	}
	*/
	
	@Override
	public List<BookDto> selectAll() {
		List<BookDto> list = session.selectList("book.selectAll");
		return list;
	}

	@Override
	public void insert(BookDto dto) {
		session.insert("book.insert", dto);
		// insert 문을 실행해서 dto 값을 넣을 것
	}

	@Override
	public int update(BookDto dto) {
		return session.update("book.update", dto);
		
	}

	@Override
	public int deleteByNum(int num) {

		return session.delete("book.delete", num);
	}

	@Override
	public BookDto getByNum(int num) {
		BookDto dto = session.selectOne("book.getByNum", num);
		return dto;
	}

}
