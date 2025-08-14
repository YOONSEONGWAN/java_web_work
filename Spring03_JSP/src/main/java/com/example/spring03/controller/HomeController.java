package com.example.spring03.controller;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.http.HttpServletRequest;
/*
 * 	@Controller 어노테이션이 붙어있기 때문에 spring 은 new HomeController() 해서 객체 생성한 뒤,
 * 
 * 	Spring bean container 에서 직접 관리를 한다.
 * 
 * 	또한 @Controller 어노테이션이 붙은 객체는 클라이언트의 요청을 처리하는 "특별한 객체"이기 때문에
 * 	해당 동작을 하기 위한 준비작업도 이루어진다. 
 */
@Controller
public class HomeController {
	/*
	 * 	http://localhost:9000/
	 * 	위처럼 이 서버의 최상위(root, ~~/ ) 경로 요청이 왔을 때 요청을 처리할 컨트롤러 메소드
	 */
	
	
	@GetMapping("/")
	public String home(HttpServletRequest request) {
		// DB 에서 읽어온 공지사항이라고 가정
		List<String> notice = List.of("Spring 프레임워크가 시작되었어요", "어쩌구예요", "저쩌구예요"); 
		
		// 응답에 필요한 data 를 HttpServletRequest 객체에 담아준다.
		request.setAttribute("notice", notice);
		
		/*
		 * 	@ResponseBody 어노테이션 없이 리턴해주는 문자열은 view page(jsp 페이지)의 위치를 의미한다.
		 * 
		 * 	"home" 을 리턴하면 application.properties 파일에 설정된 대로 
		 * 	접두어 (prefix)에 "WEB-INF/views/" 가 붙고
		 * 	접미어에 ".jsp" 가 자동으로 붙어서
		 * 	결국 view page 는 "/WEB-INF/views/home.jsp" 를 자리키는 것이다.
		 * 	해당 jsp 페이지로 응답이 위임된다. 
		 */
		return "home";
	}
}
