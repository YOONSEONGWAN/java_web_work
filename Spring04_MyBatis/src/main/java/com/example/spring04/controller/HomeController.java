package com.example.spring04.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home(Model model) {
		List<String> notice=List.of("공지사항입니다!", "어쩌구입니다!", "저쩌구입니다!");
		model.addAttribute("notice", notice);
		
		return "home";
	}
}
