package com.example.spring08.dto;

import org.springframework.web.multipart.MultipartFile;

public class GalleryUploadRequest {
	private String title;
	private String content;
	// <input type="file" name="images" multiple
	// images 라는 파라미터명으로 파일이 여러 개 전송되니까 MultipartFile[] type 으로 선언.
	private MultipartFile[] images;
}
