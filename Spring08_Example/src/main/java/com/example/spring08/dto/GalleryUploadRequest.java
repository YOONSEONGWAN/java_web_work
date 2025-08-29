package com.example.spring08.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor 
@Builder // builder 형식으로 객체 생성할 수 있도록 한다. 필드의 모든 값을 전달받는 생성자를 사용 
@Data // setter, getter + toString() 오버라이딩
public class GalleryUploadRequest {
	private String title;
	private String content;
	// <input type="file" name="images" multiple
	// images 라는 파라미터명으로 파일이 여러 개 전송되니까 MultipartFile[] type 으로 선언.
	private MultipartFile[] images;
}
