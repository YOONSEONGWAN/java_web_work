package com.example.spring08.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.spring08.dto.GalleryDto;
import com.example.spring08.dto.GalleryImageDto;
import com.example.spring08.repository.GalleryMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GalleryServiceImpl implements GalleryService {

	private final GalleryMapper mapper;
	
	@Override
	public List<GalleryDto> getList() {
		mapper.getList();
		return null;
	}

	@Override
	public List<GalleryDto> getListWithImages() {
		mapper.getListWithImages();
		return null;
	}

	@Override
	public GalleryDto getData(int num) {
		mapper.getData(num);
		return null;
	}

	@Override
	public List<GalleryImageDto> getImageList(int galleryNum) {
		mapper.getImageList(galleryNum);
		return null;
	}

	@Override
	public int saveContent(GalleryDto dto) {
		mapper.insert(dto);
		return 0;
	}

	@Override
	public void saveContentWithImages(GalleryDto dto, List<GalleryImageDto> images) {
//		// 이미지 먼저 저장
//		mapper.insertImage(null);
//		
//		mapper.insert(dto);
//		int gNum=dto.getNum();
//		
//		for (GalleryDto img : images) {
//			mapper.insertImage(img.getNum());
//			dto.setImageList(images);
//		}
//		mapper.insertImage(null);
		
	}

	@Override
	public void addImages(int galleryNum, List<GalleryImageDto> images) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateContent(GalleryDto dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteImage(int imageNum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteContent(int num) {
		// TODO Auto-generated method stub
		
	}

}
