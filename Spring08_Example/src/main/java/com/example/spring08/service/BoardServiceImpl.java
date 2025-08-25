package com.example.spring08.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.spring08.dto.BoardDto;
import com.example.spring08.dto.BoardListResponse;
import com.example.spring08.dto.CommentDto;
import com.example.spring08.repository.BoardDao;
import com.example.spring08.repository.CommentDao;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{

	// 하나의 서비스에서 여러개의 dao 에 의존하는 경우도 많다.
	private final BoardDao boardDao;
	private final CommentDao commentDao;
	
	// pageNum 또는 keyword 에 해당하는 글목록과 추가 정보를 BoardListResponse 객체에 담아서 리턴하는 메소드
	@Override
	public BoardListResponse getBoardList(int pageNum, String keyword) {
		
		// 한 페이지에 몇개씩 표시할 것인지
		final int PAGE_ROW_COUNT=10;
		
		// 하단 페이지를 몇개씩 표시할 것인지
		final int PAGE_DISPLAY_COUNT=5;
		
		// 보여줄 페이지의 시작 ROWNUM
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT; // 공차수열
		// 보여줄 페이지의 끝 ROWNUM
		int endRowNum=pageNum*PAGE_ROW_COUNT; // 등비수열
		
		//하단 시작 페이지 번호 (정수를 정수로 나누면 소수점이 버려진 정수가 나옴)
		int startPageNum = 1 + ((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//하단 끝 페이지 번호
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
			
		/*
			StringUtils 클래스의 isEmpty() static 메소드를 이용하면 문자열이 비어있는지 여부를 알 수 있다.
			null 또는 "" 의 빈 문자열은 비었다고 판단
			
			StringUtils.isEmpty(keyword)
			는
			keyword == null or "".equals(keyword)
		*/
		// 전체 글의 갯수 
		int totalRow= 0; // 기본값 설정
		if(StringUtils.isEmpty(keyword)){ // 전달된 키워드가 없으면
			totalRow=boardDao.getCount();
		}else{ // 전달된 키워드가 있다면
			totalRow=boardDao.getCountByKeyword(keyword);
		}
		
		//전체 페이지의 갯수 구하기 (double.실수로 나눠야 소숫점의 실수로 나온다.)
		int totalPageCount=(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		//끝 페이지 번호가 이미 전체 페이지 갯수보다 크게 계산되었다면 잘못된 값이다.
		if(endPageNum > totalPageCount){
			endPageNum=totalPageCount; //보정해 준다. 
		}	
		
		// dto 에 select 할 row 의 정보를 담기
		BoardDto dto=new BoardDto();
		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);
		
		// 글 목록에서
		List<BoardDto> list=null;
		// 만약 keyword 가 없다면
		if(StringUtils.isEmpty(keyword)){
			list=boardDao.selectPage(dto);
		}else{ // keyword 가 있다면 dto 에 keyword 담고 특정 키워드만 출력
			dto.setKeyword(keyword);
			list=boardDao.selectPageByKeyword(dto);
		}
		
		// 한 줄 coding 으로 BoardListResponse 객체를 만들어서 리턴하기
		return BoardListResponse.builder()
				.list(list)
				.PageNum(pageNum)
				.keyword(keyword)
				.startPageNum(startPageNum)
				.endPageNum(endPageNum)
				.totalPageCount(totalPageCount)
				.totalRow(totalRow)
				.build();
	}

	@Override
	public void createContent(BoardDto dto) {
		boardDao.insert(dto);
		// 글 정보가 저장된 이후에는 글번호가 있음
		
	}

	@Override
	public BoardDto getDetail(int num) {
		return boardDao.getByNum(num);
	}

	@Override
	public List<CommentDto> getComments(int parentNum) {
		return commentDao.selectList(parentNum);
	}

}
