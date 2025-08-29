package com.example.spring08.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.spring08.dto.CommentDto;
import com.example.spring08.dto.GalleryDto;
import com.example.spring08.dto.GalleryImageDto;
import com.example.spring08.dto.GalleryUploadRequest;
import com.example.spring08.dto.GalleryViewResponse;
import com.example.spring08.repository.CommentDao;
import com.example.spring08.repository.GalleryMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GalleryServiceImpl implements GalleryService {

	// 의존객체를 생성자로 주입받기
	private final GalleryMapper mapper;
	private final CommentDao commentDao;
	
	// 업로드 된 이미지를 저장할 위치 얻어내기 
	@Value("${file.location}") // resources/custom.properties 에서 읽어내서 필드에 담아내는 작업 수행
	private String fileLocation;
	
	@Override
	public List<GalleryDto> getGalleryList() {
		
		return mapper.getListWithImages();
	}
	
	/*
	 * 	이 서비스에서 일어나는 DB 관련 작업을 하나의 transaction 단위로 묶기
	 * 
	 * 	@Transactional 어노테이션의 동작
	 * 
	 * 	1. 작업중에 DataAccessException type 예외가 발생하면 자동 rollback
	 * 	2. @Repository 어노테이션이 붙은 dao 에서 DB 관련 작업중에 SQLException 이 발생하면
	 * 		spring 이 해당 예외를 잡아서 DataAccessException 을 자동으로 발생시킨다. 
	 * 		(transaction 에 영향을 주기 위해서)
	 * 	3. @Mapper 를 이용해서 dao 를 만들면 해당 dao 에 자동으로 @Repository 어노테이션이 붙는다.
	 * 	4. 서비스에서 어떤 동작을 하다가 에러가 난 경우 transaction 에 영향을 주고 싶으면,
	 * 		일반 예외를 발생시키지 말고 DataAccessException 을 throw 하면 transaction 관리가 된다.
	 * 	5. 커스텀 Exception 을 발생시켜서 transaction 을 관리하고 싶으면 커스텀 Exception 클래스를 만들 때,
	 * 		반드시 RuntimeException 클래스 말고 DataAccessException 클래스를 상속받아 만들고,
	 * 		특정 조건하에서 해당 Exception 을 발생시키면 자동으로 transaction 관리가 된다. 
	 */
	@Transactional
	@Override
	public void createGallery(GalleryUploadRequest galleryRequest) { // 전달해야할 것: galleryNum, saveFileName
		
		// I. get Gallery PK ( in save imagedata to db, this num is used GalleryNum) 
		int num = mapper.getSequence();
		
		// 로그인된 userName
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		// Gallery 정보를 먼저 DB 에 저장한다.
		GalleryDto dto = GalleryDto.builder()
				.num(num)
				.title(galleryRequest.getTitle())
				.content(galleryRequest.getContent())
				.writer(userName)
				.build();
		mapper.insert(dto);
		
		// II. saveFileName 
		
		// 업로드된 이미지 파일의 정보를 가지고 있는 배열을 담아준다.
		MultipartFile[] images=galleryRequest.getImages();
		
		// 반복문 돌면서 배열에 저장된 MultipartFile 객체를 순서대로 참조하면서 이미지 관련 처리를 한다.
		for(int i=0; i<images.length; i++) {
			// 업로드된 이미지 있는지 읽어와보기
			// 배열에서 원하는 인덱스에 해당하는 MultipartFile 객체를 참조한다.
			MultipartFile image=images[i];
			// 만일 업로드된 이미지가 있다면
			// 원본 파일명
			String orgFileName = image.getOriginalFilename();
			// 이미지의 확장자를 유지하기 위해 뒤에 원본 파일명을 추가
			String saveFileName = UUID.randomUUID().toString()+orgFileName;
			// 저장할 파일의 전체 경로 구성
			String filePath = fileLocation + File.separator+saveFileName;
			try {
				// 업로드된 파일을 저장할 파일 객체 생성
				File saveFile = new File(filePath);
				image.transferTo(saveFile);
			}catch(Exception e) {
				e.printStackTrace(); // 여기까지가 n번방의 이미지파일 1개를 원하는 곳에 저장하는 로직이다.
			}
			// GalleryImageDto 객체에 이미지 하나의 정보를 담고
			GalleryImageDto imageDto=GalleryImageDto.builder()
					.galleryNum(num) // sequence 로 얻은 값
					.saveFileName(saveFileName) // 파일이름
					.build();
			// save in DB
			mapper.insertImage(imageDto);
		}		
	}
	
	@Override
	public GalleryViewResponse getGallery(int num) {
		// get logined userName, 로그인 안했으면 anonymousUser
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		boolean isLogin = userName.equals("anonymousUser") ? false:true ;
		// get GalleryDto
		GalleryDto dto = mapper.getData(num);
		// Dto 의 content 에서 개행기호를 <br> 요소로 변경한다음 다시 넣기
		String result = dto.getContent().replace("\r\n", "<br>").replace("\n", "<br>");
		dto.setContent(result);
		
		// image list
		List<GalleryImageDto> images = mapper.getImageList(num);
		// comment list
		List<CommentDto> commentList=commentDao.selectList(num);	
		
		return GalleryViewResponse.builder()
				.userName(userName)
				.isLogin(isLogin)
				.dto(dto)
				.images(images)
				.commentList(commentList)
				.build();
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
