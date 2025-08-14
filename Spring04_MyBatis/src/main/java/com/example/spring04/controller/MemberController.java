package com.example.spring04.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.spring04.dto.MemberDto;
import com.example.spring04.repository.MemberDao;

@Controller
public class MemberController {
	
	// 필요한 의존 객체를 주입받는다. 
	@Autowired private MemberDao dao;
	
	// 이름과 주소를 전달하면 전달된 정보를 DB 에 저장하고 해당 정보의 PK 를 리턴하는 메소드 
	int insertFixture(String name, String addr) {
		MemberDto dto = new MemberDto();
		dto.setName(name);
		dto.setAddr(addr);
		// MyBatis 에서 selectKey 설정을 했기 때문에 insert 하고 나면 dto 객체에 추가한 회원의 번호가 들어있다.
		dao.insert(dto);
		
		return dto.getNum();
	}
	
	@GetMapping("/member/list")
	public String list(Model model) {
		// 회원목록
		List<MemberDto> list = dao.selectAll();
		// 응답에 필요한 객체를 Model 객체에 담는다
		model.addAttribute("list", list);
		
		
		// "WEB-INF/views/member/list.jsp" 에서 응답하기 
		return "member/list";
	}
	
	@GetMapping("/member/edit") // 수정하기 미완성
	public String edit(int num) {
		MemberDto member = dao.getByNum(num);
		if (member == null) return "redirect:/member/list";
		
		return "/member/list";
		
	}

}
