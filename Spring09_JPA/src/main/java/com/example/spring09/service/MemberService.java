package com.example.spring09.service;

import java.util.List;

import com.example.spring09.dto.MemberDto;
import com.example.spring09.dto.MemberListRequest;
import com.example.spring09.dto.MemberPageResponse;

public interface MemberService { // 인터페이스 만들기. 구현클래스를 만들어 사용
	public List<MemberDto> getAll();
	public MemberDto getMember(int num);
	public MemberDto addMember(MemberDto dto);
	public void updateMember(MemberDto dto);
	public MemberDto deleteMember(int num);
	public MemberPageResponse getPage(MemberListRequest request);
}
