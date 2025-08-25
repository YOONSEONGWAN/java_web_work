package com.example.spring08.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.spring08.dto.CommentDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository // bean 으로 관리
public class CommentDaoImpl implements CommentDao {
	
	private final SqlSession session;

	// 원글의 글번호를 이용해서 원글에 달린 댓글 목록을 리턴하는 메소드
	@Override	
	public List<CommentDto> selectList(int parentNum) {
		
		return session.selectList("comment.selectList", parentNum);
	}
}
