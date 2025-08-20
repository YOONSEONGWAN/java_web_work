package com.example.spring05;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TestController {
	
	@GetMapping("/javascript")
	public String javascript(Model model) {
		//로그인여부
		model.addAttribute("isLogin", false);
		//나이
		model.addAttribute("age", 15);
		//이름
		model.addAttribute("name", "김구라");
		
		//회원 한명의 정보 
		MemberDto dto=MemberDto.builder()
				.num(1)
				.name("김구라")
				.addr("노량진")
				.build();
		// 해당 데이터를 Model 객체에 담고 
		model.addAttribute("dto", dto);
		
		//DB 에서 select 한 결과라고 가정하자 
		MemberDto dto1=MemberDto.builder().num(1).name("김구라").addr("노량진").build();
		MemberDto dto2=MemberDto.builder().num(2).name("해골").addr("행신동").build();
		MemberDto dto3=MemberDto.builder().num(3).name("원숭이").addr("상도동").build();
		
		// read only List 
		List<MemberDto> list=List.of(dto1, dto2, dto3);
		
		//Model 객체에 "list" 라는 키값으로 담기
		model.addAttribute("list", list);
		
		return "javascript";
	}
	
	@GetMapping("/unescape")
	public String unescape(Model model) {
		// html 형식의 문자열을 templates 페이지에 전달할 일도 있다.
		String content="""
			<ul>
				<li>하나</li>
				<li>둘둘</li>
				<li>셋셋</li>
			</ul>	
		""";
		model.addAttribute("content", content);
		
		return "unescape";
	}
	
	@GetMapping("/print-num")
	public String printNum(Model model) {
		
		// 테스트를 위해 데이터 전달
		model.addAttribute("start", 6);
		model.addAttribute("end", 10);
		
		return "print-num";
	}
	
	@GetMapping("/include-test")
	public String includeTest(Model model) {
		// test 를 위한 데이터 전달
		model.addAttribute("title", "오늘의 운세");
		model.addAttribute("content", "동쪽으로 가면 귀인을 만나요 ! ");
		
		return "include-test";
	}
	
	
	/*
	 * 	컨트롤러의 메소드에 UserDto 를 선언하면 폼 전송되는 파라미터가 자동 추출되어
	 * 	UserDto 의 필드에 담긴채로 전달된다.
	 * 
	 * 	@ModelAttribute 어노테이션을 이용하면 view page 에서 해당 객체에 담긴 값을 활용할 수 있다.
	 */
	@PostMapping("/save")
	public String save(@ModelAttribute("dto") UserDto dto) {
		// "dto" 라는 키값으로 UserDto 객체가 Model 객체에 자동으로 담긴다.
		return "save"; 
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		
		return "form";
	}
	
	@GetMapping("/if")
	public String ifTest(Model model) {
		
		// view page 에서 if 문을 테스트할 값을 Model 객체에 담기 
		model.addAttribute("score", 75);
		model.addAttribute("age", 25);
		model.addAttribute("role", "staff");
		
		return "if";
	}
	
	@GetMapping("/member/list")
	public String memberList(Model model) {
		// DB 에서 불러온 회원 한 명의 정보라고 가정
		MemberDto dto1 = MemberDto.builder().num(1).name("김구라").addr("노량진").build();
		MemberDto dto2 = MemberDto.builder().num(2).name("김구라2").addr("노량진2").build();
		MemberDto dto3 = MemberDto.builder().num(3).name("김구라3").addr("노량진3").build();
		List<MemberDto> list = List.of(dto1, dto2, dto3);
		// 응답에 필요한 정보를 Model 객체에 담기
		model.addAttribute("list", list);
		// /templates/member/list.html 페이지로 응답하기
		return "member/list";
	}
	
	@GetMapping("/member/detail")
	public String memberDetail(Model model) {
		// DB 에서 불러온 회원 한 명의 정보라고 가정
		MemberDto dto = MemberDto.builder().num(1).name("김구라").addr("노량진").build();
		// 응답에 필요한 정보를 Model 객체에 담기
		model.addAttribute("dto", dto);
		// /templates/member/detail.html 을 Thymeleaf 페이지로 응답하기
		return "member/detail";
	}
}
