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
	
	@PostMapping("/board/update")
	public String boardUpdate(@ModelAttribute BoardDto dto) {
		
		service.updateContent(dto);
		
		return "redirect:/board/view?num=" + dto.getNum();
	}
	
	@GetMapping("/board/edit")
	public String boardEdit(@RequestParam int num, Model model) {
		
		BoardDto dto = service.getDetail(num);
		model.addAttribute("dto", dto);  
		
		return "board/edit";
	}
	
	@GetMapping("/board/delete")
	public String boardDelete(@RequestParam int num) {
	    service.deleteContent(num);
	    return "redirect:/board/list";
	}
	
	@PostMapping("/board/comment-update")
	public String commentUpdate(CommentDto dto) {
		
		service.updateComment(dto);
		
		return "redirect:/board/view?num=" + dto.getParentNum();
	}
	
	@GetMapping("/board/comment-delete")
	public String commentDelete(CommentDto dto) {
		// dto 에는 삭제할 댓글의 글번호와 원글의 글번호가 들어있다 (num, parentNum)
		service.deleteComment(dto.getNum());
		
		return "redirect:/board/view?num=" + dto.getParentNum();
	}
	
	@PostMapping("/board/save-comment")
	public String commentSave(@ModelAttribute CommentDto comment) { 
		// dto 에 담겨오는 것 : parentNum,targetWriter, content, groupNum(댓글의 댓글)
		service.createComment(comment);
		// 댓글을 작성한 원글 자세히 보기로 리다일렉트 
		return "redirect:/board/view?num=" + comment.getParentNum();
	}
	
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
		boolean isLogin =userName.equals("anonymousUser")? false : true;
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
		
		return "/board/save";
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
			BoardDto dto) {
		// BoardDto 객체에는 keyword 와 search 가 있을수도 있다. (없으면 null)
		
		// 응답에 필요한 데이터를 얻어내서
		BoardListResponse listResponse = service.getBoardList(pageNum, dto);
		// 모델 객체에 담고
		model.addAttribute("dto", listResponse);
		
		// 타임리프 페이지에서 응답 
		return "board/list";
	}
}
