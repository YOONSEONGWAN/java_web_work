package com.example.spring08.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardDto {
	private int num;
	private String writer;
	private String title;
	private String content;
	private int viewCount;
	private String createdAt;
	// 페이징 처리 위한 필드
	private int startRowNum;
	private int endRowNum;
	// 프로필 이미지 출력을 위한 필드
	private String profileImage;
	// 이전글, 다음글 처리를 위한 필드
	private int prevNum;
	private int nextNum;
	// 검색 키워드를 담기 위한 필드
	private String keyword;
	
	
}
