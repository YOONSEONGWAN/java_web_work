package com.example.spring08.repository;

import java.util.List;

import com.example.spring08.dto.BoardDto;

public interface BoardDao {
	public List<BoardDto> selectPage(BoardDto dto);
	public List<BoardDto> selectPageByKeyword(BoardDto dto);
	public int getCount();
	public int getCountByKeyword(String keyword);
	public void insert(BoardDto dto);
	public BoardDto getByNum(int num);
}
