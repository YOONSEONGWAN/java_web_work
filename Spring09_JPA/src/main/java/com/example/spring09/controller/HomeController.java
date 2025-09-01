package com.example.spring09.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  // 이 어노테이션이 있어서 HomeController 객체는 자동 생성되고 bean 으로 관리된다.
public class HomeController {
	
	// @Controller 어노테이션으로 bean 이 되는 객체는 클라이언트의 요청을 처리할 수 있다.
	@GetMapping("/")
	public String home(Model model) { // 컨트롤러 메소드의 매개변수에 Model 을 선언하면 객체가 자동으로 전달된다.
		// 응답에 필요한 데이터는 Model 객체에 담는다.
		// Model 객체에 담으면 자동으로 HttpServeltRequest 객체에 담긴다. 
		List<String> notice=List.of("공지사항입니다!", "어쩌구입니다!", "저쩌구입니다!");
		// "notice"라는 키값으로 List<String> type 을 담는다. 
		model.addAttribute("notice", notice);
		/*
		 * 	여기서 "home" 이라는 문자열을 리턴하면
		 * 	접두어로 "/WEB-INF/views/" 가 붙고
		 * 	접미어로 ".jsp" 가 붙어서
		 * 	"/WEB-INF/views/home.jsp" 라는 view page 가 구성된다.
		 * 	따라서 해당 jsp 페이지로 응답이 위임되어 응답하게 되는 것 
		 * 	home.jsp 페이지에서는 request 영역에 "notice" 라는 키값으로 담긴 데이터를
		 * 	EL 표현식을 이용해서 추출할 수 있음 => ${requestScope.notice} or ${notice}
		 */
		return "home";
	}
}
