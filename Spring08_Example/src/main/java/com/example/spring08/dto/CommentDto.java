package com.example.spring08.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDto {
	private int num;
	private String writer;
	private String content;
	private String targetWriter;
	private int groupNum;
	private int parentNum;
	private String deleted;
	private String createdAt;
	private String profileImage; // 프로필 이미지 출력 위한 필드
	// 대댓글의 갯수를 저장하기 위한 필드 
	private int replyCount;
	
}
