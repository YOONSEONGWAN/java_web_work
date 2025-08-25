package com.example.spring08.service;

import java.util.List;

import com.example.spring08.dto.BoardDto;
import com.example.spring08.dto.BoardListResponse;
import com.example.spring08.dto.CommentDto;

public interface BoardService {
	
	public BoardListResponse getBoardList(int pageNum, String keyword);
	public void createContent(BoardDto dto);
	public BoardDto getDetail(int num);
	public List<CommentDto> getComments(int parentNum);
}
