package com.example.spring09.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring09.dto.MemberDto;
import com.example.spring09.dto.MemberListRequest;
import com.example.spring09.dto.MemberPageResponse;
import com.example.spring09.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/v2")
@RestController
public class RestMemberController2 {
	
	private final MemberService memberService;
	
	// 회원 한 명의 정보 수정 요청 처리
	@PutMapping("/members/{num}")
	public MemberDto update(@PathVariable int num, @Valid @RequestBody MemberDto dto) { // json 으로 줄 거라면 @RequestBody 필요하다.
		dto.setNum(num);
		memberService.updateMember(dto);
		return dto;
	}
	
	@PostMapping("/members")
	public MemberDto create(@Valid @org.springframework.web.bind.annotation.RequestBody MemberDto dto) {// @Valid 어노테이션 추가해서 검증도 수행한다
		
		return memberService.addMember(dto);
	}
	
	@GetMapping("/members")
	public MemberPageResponse list(MemberListRequest request) {
		
		return memberService.getPage(request);
	}

}
