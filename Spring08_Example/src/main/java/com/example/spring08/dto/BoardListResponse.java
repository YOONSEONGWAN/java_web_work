package com.example.spring08.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 응답에 필요한 데이터를 하나의 객체에 담을 수 있도록 클래스 설계
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardListResponse {
	// 글 목록
	private List<BoardDto> list;
	private int startPageNum;
	private int endPageNum;
	private int totalPageCount;
	private int PageNum;
	private int totalRow;
	private String keyword; // 검색 키워드
	private String search; // 검색 조건
	private String query; // GET 방식 파라미터 "search=xxx&keyword=xxx" or "" 예시) search=writer&keyword=kimgura
}
