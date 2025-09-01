package com.example.spring09.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.spring09.dto.MemberDto;
import com.example.spring09.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	// 필요한 의존 객체를 주입받는다. 
	private final MemberService service;
	
	// 이름과 주소를 전달하면 전달된 정보를 DB 에 저장하고 해당 정보의 PK 를 리턴하는 메소드 
	int insertFixture(String name, String addr) {
		MemberDto dto = new MemberDto();
		dto.setName(name);
		dto.setAddr(addr);
		// MyBatis 에서 selectKey 설정을 했기 때문에 insert 하고 나면 dto 객체에 추가한 회원의 번호가 들어있다.
		 service.addMember(dto);
		
		return dto.getNum();
	}
	
	@PostMapping("/member/update") 
	public String update(MemberDto dto) {
		// 수정할 회원의 정보가 MemberDto 객체에 담겨 전달된다.
		 service.updateMember(dto);
		return "member/update";
	}
	
	@GetMapping("/member/edit")
	public String edit(int num, Model model) {
		// 수정할 회원의 정보를 얻어와서
		MemberDto dto= service.getMember(num);
		// Model 객체에 담고
		model.addAttribute("dto", dto);
		// view page 로 forward 이동해서 응답
		return "member/edit";
	}
	
	
	@GetMapping("/member/delete")
	public String delete(@RequestParam("num") int num, Model model) {
		/*
		 * 	매개변수에 int num 을 선언하면 요청 파라미터 중에서 num 이라는 파라미터 명으로 전달되는
		 * 	문자열을 자동 추출해서 integer.parseInt() 수행하고 실제 int 값으로 바꾼 뒤
		 * 	해당 값을 매개변수에 전달해준다.
		 * 	int 값으로 바꿀 수 없는 문자열이 넘어오면 에러가 발생
		 */
		 service.deleteMember(num);
		 model.addAttribute("num", num);
		return "member/delete";
	}
	
	
	@PostMapping("/member/save") // 매핑 경로 작성해줄것.
	public String save(MemberDto dto) { //  매개변수에 dto 선언하기: 요청 파라미터 추출하는 간단한 방법
		/*
		 * 	매개변수에 MemberDto 를 선언하면 폼 전송되는 파라미터가 자동으로 추출되어서 (단, @setter 가 있어야함
		 * 	MemberDto 객체에 담긴 채 전달된다
		 * 	단 MemberDto 클래스의 필드명과 폼 전송되는 파라미터명이 같아야한다. dto 의 값이 name 이면 폼 전송 name="name" 이어야 함
		 * 	private String name <=> <input type="text" name="name" > 
		 * 	private String addr <=> <input type="text" name="addr" > 
		 */
		 service.addMember(dto);
		
		// 인서트 후 회원 목록보기로 "/member/list" 요청을 다시 하라는 redirect 응답 하기
		// "redirect:리다일렉트 경로" 처럼 문자열을 리턴하면 된다.
		return "redirect:/member/list";
	}
	
	@GetMapping("/member/new-form")
	public String newForm() {
		// 현재 여기서 수행할 로직은 없고 view page 위치만 리턴
		return "member/new-form";
	}
	
	@GetMapping("/member/list")
	public String list(Model model) {
		// 회원목록
		List<MemberDto> list =  service.getAll();
		// 응답에 필요한 객체를 Model 객체에 담는다
		model.addAttribute("list", list);
		
		
		// "WEB-INF/views/member/list.jsp" 에서 응답하기 
		return "member/list";
	}
	

}
