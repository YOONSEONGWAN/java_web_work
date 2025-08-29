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
public class GalleryViewResponse {
	private GalleryDto dto;
	private List<GalleryImageDto> images;
	private List<CommentDto> commentList;	
}
