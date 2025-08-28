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
public class GalleryImageDto {
	private	int num;
	// gallery table 의 PK 참조 
	private int galleryNum;
	private String saveFileName;
	private String createdAt;
	
	
}
