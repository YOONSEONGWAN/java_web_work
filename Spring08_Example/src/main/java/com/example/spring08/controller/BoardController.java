package com.example.spring08.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.spring08.dto.BoardDto;
import com.example.spring08.dto.BoardListResponse;
import com.example.spring08.dto.CommentDto;
import com.example.spring08.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {
	
	private final BoardService service;
	
	@GetMapping("/board/view")
	public String boardView(int num, Model model) {
		// service 이용해 필요한 데이터 얻어내서
		BoardDto dto = service.getDetail(num);
		List<CommentDto> comments=service.getComments(num);
		// model 객체에 담고
		model.addAttribute("dto", dto);
		model.addAttribute("commentList", comments);
		// 로그인된 유저네임 얻어내기 
		// 로그인을 안 했으면 "annonymousUser"가 리턴된다.)
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		boolean isLogin =userName.equals("annonymousUser")? false : true;
		// 위의 추가 정보도 모델 객체에 담는다.
		model.addAttribute("userName", userName);
		model.addAttribute("isLogin", isLogin);
		
		// 타임리프 페이지에서 응답
		return "board/view";
	}
	
	/*
	 * 	@ModelAttribute 는 view page 에서 필요한 값을 대신 Model 객체에 담아준다. 
	 */
	@PostMapping("/board/save")
	public String boardSave(@ModelAttribute("dto") BoardDto dto) { // dto 에 담겨오는 것: title, content
		// 글 작성자
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		dto.setWriter(userName);
		// Service 이용해서 글 저장하기 
		service.createContent(dto);
		
		return "board/save";
	}
	
	@GetMapping("/board/new-form")
	public String newForm() {
		
		return "board/new-form";
	}
	
	
	/*
	 * 	@RequestParam 어노테이션을 이용하면 요청 파라미터를 추출하면서 해당 값이 없으면 defaultValue 를 설정할 수 있다. 
	 */
	@GetMapping("/board/list")
	public String list(Model model, 
			@RequestParam(defaultValue = "1") int pageNum, 
			@RequestParam(defaultValue = "") String keyword) {
		// 응답에 필요한 데이터를 얻어내서
		BoardListResponse listResponse = service.getBoardList(pageNum, keyword);
		// 모델 객체에 담고
		model.addAttribute("dto", listResponse);
		
		// 타임리프 페이지에서 응답 
		return "board/list";
	}
}
