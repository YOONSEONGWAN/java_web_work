package com.example.spring09.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring09.dto.MemberDto;
import com.example.spring09.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

// 컨트롤러에서 리턴하는 데이터를 json 으로 응답하고자 할 때 사용하는 어노테이션
@RequestMapping("/v1")
@RestController
@RequiredArgsConstructor
@Tag(name = "Members", description = "회원 관리 API") // swagger ui 에 제공할 정보 
public class RestMemberController {
	
	private final MemberService memberService;
	
	@Operation(summary = "회원목록 조회", description="전체 회원을 페이징 없이 반환합니다.")
	@GetMapping("/members")
	public List<MemberDto> list(){
		List<MemberDto> list = memberService.getAll();
		return list;
	}
	
	// 회원 한 명의 정보 추가 요청 처리
	@PostMapping("/members")
	public MemberDto create(@RequestBody MemberDto dto) {
		
		return memberService.addMember(dto);
		
	}
	
	// 회원 한 명의 정보 요청 처리
	@GetMapping("/members/{num}")
	public MemberDto detail(@PathVariable int num) {
		return memberService.getMember(num);
	}
	
	// 회원 한 명의 정보 삭제 요청 처리
	@DeleteMapping("/members/{num}")
	public MemberDto delete(@PathVariable int num) {
		return memberService.deleteMember(num);
	}
	
	// 회원 한 명의 정보 수정 요청 처리
	@PutMapping("/members/{num}")
	public MemberDto update(@PathVariable int num, @RequestBody MemberDto dto) { // json 으로 줄 거라면 @RequestBody 필요하다.
		dto.setNum(num);
		memberService.updateMember(dto);
		return dto;
	}
	
	
	@GetMapping("/member/hello")
	public String hello() {
	
		return "hello, world"; 
	}
	
	@GetMapping("/member/hello2")
	public MemberDto hello2() {
		
		return MemberDto.builder().num(1).name("김구라").addr("노량진").build();
	}
	@GetMapping("/member/hello3")
	public List<String> hello3(){
		return List.of("김구라","해골","원숭이");
	}
}
