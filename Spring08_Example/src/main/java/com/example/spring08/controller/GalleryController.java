package com.example.spring08.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.spring08.dto.GalleryUploadRequest;
import com.example.spring08.dto.GalleryViewResponse;
import com.example.spring08.service.GalleryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class GalleryController {
	
	private final GalleryService galleryService;
	
	
	
	@GetMapping("/gallery/view")
	public String galleryView(int num, Model model) {
		
		GalleryViewResponse response = galleryService.getGallery(num);
		model.addAttribute("res", response);
		
		return "gallery/view";
	}

	@PostMapping("/gallery/save")
	public String gallerySave(GalleryUploadRequest uploadRequest) { // title, content, images 가 넘어옴
		// 업로드된 파일의 모든 정보를 GalleryUploadRequest 의 images 라는 MultipartFile[] 객체에 담아서
		// 전달해준다
		galleryService.createGallery(uploadRequest);
		
		return "redirect:/gallery/list";
	}
	
	@GetMapping("/gallery/new-form")
	public String newFrom() {
		
		return "gallery/new-form";
	}
	
	@GetMapping("/gallery/list")
	public String galleryList(Model model) {
		// 응답에 필요한 객체를 "list" 라는 키값으로 담고
		model.addAttribute("list", galleryService.getGalleryList());
		// Thymeleaf 페이지에서 목록을 응답한다. 
		return "gallery/list";
	}
}
