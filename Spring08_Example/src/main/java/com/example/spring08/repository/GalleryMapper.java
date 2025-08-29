package com.example.spring08.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.example.spring08.dto.GalleryDto;
import com.example.spring08.dto.GalleryImageDto;

@Mapper
public interface GalleryMapper {

		
		
	    // 저장할 글번호를 리턴해주는 메소드
	    @Select("SELECT gallery_seq.NEXTVAL AS num FROM DUAL")
	    public int getSequence();
	    
	    // 게시글 저장
		@Insert("""
			INSERT INTO gallery (num, title, writer, content)
			VALUES (#{num}, #{title}, #{writer}, #{content})
			""")
		/*@SelectKey(statement = "SELECT gallery_seq.NEXTVAL FROM DUAL",
			keyProperty = "num", resultType = int.class, before = true)*/
		public void insert(GalleryDto dto);
	    
	    // 이미지 저장
		@Insert("""
			INSERT INTO gallery_image (num, galleryNum, saveFileName)
	        VALUES (gallery_image_seq.NEXTVAL, #{galleryNum}, #{saveFileName})
			""")
		public void insertImage(GalleryImageDto dto);
		/*
		 * 	Service 단에서 할 것
		 * 	GalleryNum 얻어내기
		 * 	mapper.insert(dto);
         *	int newNum = dto.getNum(); // ← 여기서 새 글번호 획득
		 *
         *	// 2) 이미지들 저장: FK에 방금 얻은 글번호 세팅
		 *	for (GalleryImageDto img : images) {
	     *      img.setGalleryNum(newNum);
	     *      mapper.insertImage(img);
		 */
		
		
	    
	    // 게시글 목록
		@Select("""
			SELECT num, title, writer, TO_CHAR(createdAt, 'YYYY-MM-DD HH24:MI:SS') AS createdAt
	        FROM gallery
	        ORDER BY num DESC
			""")
		public List<GalleryDto> getList();
	    
		
	    /*
	     *	상세보기
	     * 	
	     * 	메소드의 리턴타입이 GalleryDto 이기 때문에
	     * 	SELECT 된 row 1 개의 정보가 Dto 객체에 담겨 리턴됨
	     * 	단) SELECT 문의 칼럼명과 GalleryDto 의 필드명이 일치해야 자동으로 담김
	     * 	메소드의 매개변수의 type 이 해당 SELECT 문의 parameterType 으로 설정된다.
	     */ 
		@Select("""
			SELECT g.num, g.title, g.writer, g.content, 
				TO_CHAR(g.createdAt, 'YYYY-MM-DD HH24:MI:SS') AS createdAt, 
	    		u.profileImage
	        FROM gallery g
	        INNER JOIN users u ON g.writer = u.userName
	        WHERE g.num = #{num}
			""")
		public GalleryDto getData(int num);
	    
	    /*
	     * 	이미지 목록
	     * 
	     * 	1. parameterType 은 int
	     * 	2. SELECT 된 ROW 가 여러개니까 return type 이 List 이고
	     * 	3. List 의 generic type 이 GalleryImageDto 이니까 resultType 은 GalleryDto 가 된다.
	     */
		@Select("""
			SELECT num, saveFileName, TO_CHAR(createdAt, 'YYYY-MM-DD HH24:MI:SS') AS createdAt
	        FROM gallery_image
	        WHERE galleryNum = #{num}
	        ORDER BY num ASC
			""")
		public List<GalleryImageDto> getImageList(int num);
	    
	    // Mapper xml 작성 내용을 사용해야 하기 때문에 어노테이션 없이 메소드 만든다.
		
		public List<GalleryDto> getListWithImages(); // <select id="getListWithImages" >
													// 이미지 목록도 포함하여 gallery 목록 반환
		
}
