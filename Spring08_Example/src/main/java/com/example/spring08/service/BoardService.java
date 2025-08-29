package com.example.spring08.service;

import java.util.List;

import com.example.spring08.dto.BoardDto;
import com.example.spring08.dto.BoardListResponse;
import com.example.spring08.dto.CommentDto;

public interface BoardService {
	
	public BoardListResponse getBoardList(int pageNum, BoardDto dto);
	public void createContent(BoardDto dto);
	public void updateContent(BoardDto dto); // 글 수정
	public void deleteContent(int num); // 글 삭제
	public BoardDto getDetail(BoardDto dto); // 글 자세히 보기를 보여주기 위한 서비스 메소드
	public BoardDto getData(int num); // 수정할 글 정보를 보여주기 위한 서비스 메소드
	

}
