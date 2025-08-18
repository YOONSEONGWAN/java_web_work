package com.example.spring04.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 예외처리를 할 컨트롤러에는 @ControllerAdvice 어노테이션을 붙여준다. 예외메소드의 경우에는 여러 개 붙여줄 수 있다. -> 디테일한 예외처리 가능
@ControllerAdvice
public class ExceptionController {
	/*
	 * 	Spring 프레임워크가 동작하는 과정중에 DataAccessException type 의 예외가 발생하면
	 * 	이 메소드가 자동으로 호출된다.
	 * 	메소드의 매개 변수에는 해당 예외 객체의 참조값이 전달된다.
	 * 	예외 관련 처리를 하고 에러 페이지를 응답할 수 있다.
	 */

	@ExceptionHandler(DataAccessException.class) // 이 예외를 처리하겠다는 어노테이션. 
	public String dataAccess(DataAccessException dae, Model model) { // 예외 객체, 모델객체가 전달되어 응답할 수 있다.
		model.addAttribute("title", "DB 관련 작업 중 예외가 발생했습니다");
		model.addAttribute("message", dae.getMessage()); // 예외 원인을 읽어내는 메소드
		model.addAttribute("status", 500);
		
		// /WEB-INF/views/error/data-access.jsp 페이지이ㅔ서 에러 정보를 응답한다.
		return "error/data-access";
	}
	
	@ExceptionHandler(MemberException.class)
	public String memberException(MemberException me, Model model) {
		model.addAttribute("title", "Member 관련 작업 중 에러가 발생했습니다.");
		model.addAttribute("message", me.getMessage());
		model.addAttribute("reason", me.reason.name());
		
		return "error/member-exception";
	}
}
