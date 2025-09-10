package com.example.spring09.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping("/members")
	public MemberDto create(@Valid @org.springframework.web.bind.annotation.RequestBody MemberDto dto) {// @Valid 어노테이션 추가해서 검증도 수행한다
		
		return memberService.addMember(dto);
	}
	
	@GetMapping("/members")
	public MemberPageResponse list(MemberListRequest request) {
		
		return memberService.getPage(request);
	}

}
