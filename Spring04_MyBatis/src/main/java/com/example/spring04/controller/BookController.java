package com.example.spring04.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.spring04.dto.BookDto;
import com.example.spring04.service.BookService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BookController {
	private final BookService service;
	
	@PostMapping("/book/update")
	public String update(BookDto dto) {
		service.updateBook(dto);
		return "redirect:/book/list";
	}
	@PostMapping("/book/save")
	public String save(BookDto dto) {
		service.addBook(dto);
		return "redirect:/book/list";
	}
	
	@GetMapping("/book/edit")
	public String edit(int num, Model model) {
		BookDto dto = service.getBook(num);
		model.addAttribute("dto", dto);
		return "book/edit";
	}
	
	
	@GetMapping("/book/delete")
	public String delete(@RequestParam("num") int num, Model model) {
		service.deleteBook(num);
		model.addAttribute("num", num);
		return "book/delete";
	}
	
	@GetMapping("/book/new-form")
	public String newForm() {
		// page 위치만 리턴
		return "book/new-form";
	}
	
	
	@GetMapping("/book/list")
	public String list(Model model) {
		List<BookDto> list=service.getAll();
		model.addAttribute("list", list);
		return "book/list";
	}
}
