package com.example.spring08.service;

import java.util.List;

import com.example.spring08.dto.GalleryDto;
import com.example.spring08.dto.GalleryImageDto;

public interface GalleryService {
	
	
	public List<GalleryDto> getGalleryList();// 게시글 전체 목록 (필요시 목록 전용 DTO로 교체) 
    public List<GalleryDto> getListWithImages(); // (옵션) 목록을 이미지 포함 형태로 받고 싶을 때 
    public GalleryDto getData(int num); // 게시글 상세 (글 본문)
    public List<GalleryImageDto> getImageList(int galleryNum); // 해당 글의 이미지 목록
    public int saveContent(GalleryDto dto); // 글만 저장 (시퀀스는 mapper가 dto.num에 채워준다) → 새 글번호 반환
    public void saveContentWithImages(GalleryDto dto, List<GalleryImageDto> images); // 글 + 이미지들 한번에 저장 (트랜잭션)
    public void addImages(int galleryNum, List<GalleryImageDto> images); // 기존 글에 이미지 추가
    public void updateContent(GalleryDto dto); // 글 수정 (제목/내용 등)
    public void deleteImage(int imageNum); // 이미지 한 장 삭제
    public void deleteContent(int num); // 글 삭제 (이미지들도 함께 정리)
}
