package com.example.spring08.service;

import java.util.List;

import com.example.spring08.dto.GalleryDto;
import com.example.spring08.dto.GalleryImageDto;
import com.example.spring08.dto.GalleryUploadRequest;
import com.example.spring08.dto.GalleryViewResponse;

public interface GalleryService {
	
	
	public List<GalleryDto> getGalleryList();// 게시글 전체 목록 (필요시 목록 전용 DTO로 교체) 
	public void createGallery(GalleryUploadRequest galleryRequest); // 글과 이미지를 저장
	public GalleryViewResponse getGallery(int num); // 상세보기 
	
}
