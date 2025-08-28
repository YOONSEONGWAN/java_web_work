package com.example.spring08.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GalleryDto {
	
	private int num;
	private String title;
	private String writer;
	private String content;
	private String createdAt;
	// 하나의 갤러리 글에 업로드된 사진이 여러개일 수 있다.
	// 사진 하나당 GalleryImageDto 객체 하나를 쓴다.
	// 사진이 여러개 이니까 List<GalleryImageDto> type 의 필드가 필요하다.
	private List<GalleryImageDto> imageList;
	
	// profile image field
	private String profileImage;
	private String caption;
	private MultipartFile image;
}
