package com.example.spring08.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String boardUpdate(BoardDto dto, RedirectAttributes ra) {
		// 넘어오는 값: num, writer, title, content
		// 글 수정 반영하고
		service.updateContent(dto);
		// 리다일렉트 이동해서 출력할 메시지도 담는다. 
		// 모델에 담으면 redirect 이동할 때 쓸 수가 없다.(모델은 포워드만 가능)
		ra.addFlashAttribute("message", "게시글을 성공적으로 수정했습니다.");
		// 자세히보기로 리다일렉트 이동
		return "redirect:/board/view?num=" + dto.getNum();
	}
	
	@GetMapping("/board/edit")
	public String boardEdit(int num, Model model) {
		
		// 서비스가 리턴해주는 값 바로 담아보기
		model.addAttribute("dto", service.getData(num));  
		
		return "board/edit";
	}
	
	@GetMapping("/board/delete")
	public String boardDelete(@RequestParam int num) {
	    service.deleteContent(num);
	    return "board/delete";
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
	public String boardView(BoardDto requestDto,  Model model) {
		/*
		 * 	requestDto 에는 자세히 보여줄 글의 num 와
		 * 	search (검색조건), keyword (검색어 키워드) 가 들어있을 수 있다. 
		 * 	-> 서비스 메소드를 호출할 때 세가지를 넘겨주어야함 -> Dto 전달해야 
		 * 	검색어가 없는 경우는 search 와 keyword 에는 null 이 들어있다.  
		 */
		
		// service 이용해 필요한 데이터 얻어내서
		BoardDto dto = service.getDetail(requestDto);
		
		String query="";
		if(requestDto.getKeyword() !=null) {
			query="&search="+requestDto.getSearch()+"&keyword="+requestDto.getKeyword();
		}
		// 검색 query 정보도 view page 에 전달한다.
		model.addAttribute("query", query);
		
		// 댓글 목록은 원글의 글번호를 전달해서 얻어낸다
		List<CommentDto> comments=service.getComments(requestDto.getNum());
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
