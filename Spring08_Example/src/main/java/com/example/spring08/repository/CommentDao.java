package com.example.spring08.repository;

import java.util.List;

import com.example.spring08.dto.CommentDto;

public interface CommentDao {
	public List<CommentDto> selectList(int parentNum);
}
