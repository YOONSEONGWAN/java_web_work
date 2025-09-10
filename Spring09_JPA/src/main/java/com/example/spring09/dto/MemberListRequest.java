package com.example.spring09.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberListRequest {
	// 페이지 번호. 초기값(default)은 1페이지
	private int pageNum=1;
	// 검색 조건
	private String condition;
	// 검색 키워드
	private String keyword;
}
