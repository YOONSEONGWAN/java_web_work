package com.example.spring02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.spring02.dto.MemberDto;

import jakarta.servlet.http.HttpServletRequest;

// 이 클래스로 생성된 객체가 bean 이 되도록 어노테이션을 붙인다. 
// @Controller 어노테이션으로 bean 이 된 객체는 클라이언트의 요청을 처리할 수 있다. 
@Controller
public class TestController {
	/*
	 * 	매개변수에 dto 를 선언하면 Spring 프레임 워크가 요청 파라미터를 모두 추출해서
	 * 	dto 의 필드명과 일치하면 알아서 dto 의 setter 메소드를 호출해서 값을 넣은 dto 객체 전달해준다.
	 */
	@PostMapping("/member/add2")
	@ResponseBody
	public String memberAdd2(MemberDto dto) {
		System.out.println("번호"+dto.getNum());
		System.out.println("이름"+dto.getName());
		System.out.println("주소"+dto.getAddr());
		return "회원정보를 추가했습니다.";
	}
	
	@PostMapping("/member/add")
	@ResponseBody
	public String members(int num, String name, String addr) {
		
		System.out.println(num+"|"+name+"|"+addr);
		
		
		return "회원 정보를 추가했습니다!";
	}
	
	
	/*
	 * 	@RequestParam 어노테이션을 이용해서 추출된 요청 파라미터를 전달받을 수 있다. 
	 * 
	 * 	매개변수명을 input 요소 속성의 name 값과 일치 시키면 @RequestParam 은 생략 가능 
	 * 
	 * 	<input name="msg" > 와 String msg
	 */
	@PostMapping("/send2")
	@ResponseBody
	public String send2(@RequestParam String msg) {
		// 콘솔창에 출력
		System.out.println(msg);
		return "/send okay!";
	}
	
	// HttpServletRequest 객체가 필요하다면 매개변수에 선언하면 Spring 프레임워크가 전달해준다.
	@PostMapping("/send")
	@ResponseBody
	public String send(HttpServletRequest request) {
		// 요청 파라미터 추출
		String msg=request.getParameter("msg");
		// 콘솔창에 출력
		System.out.println(msg);
		
		return "/send okay!";
	}
	
	@GetMapping("/person-today") // GET 방식 "/person-today" 요청을 처리하겠다는 의미 
	@ResponseBody // ---------	 // 이 메소드에서 리턴해주는 문자열을 그대로 클라이언트에게 응답하라는 의미
	public String personToday() {
		return "오늘의 인물은 윤성완";
	}
	
	
	@GetMapping("/fortune") 
	@ResponseBody
	public String fortune() {
		
		return "동쪽으로 가면 귀인을 만나요";
	}
	

}
