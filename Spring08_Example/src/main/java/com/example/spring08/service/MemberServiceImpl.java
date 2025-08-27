package com.example.spring08.service;

import java.security.cert.PKIXReason;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring08.dto.MemberDto;
import com.example.spring08.exception.MemberException;
import com.example.spring08.exception.MemberException.Reason;
import com.example.spring08.repository.MemberMapper;

import lombok.RequiredArgsConstructor;


// 서비스 클래스에 붙여줄 어노테이션
@Service
@RequiredArgsConstructor // lombok 이 생성자를 자동으로 만들어준다.
public class MemberServiceImpl implements MemberService{
	// 의존 객체에 final 예약어를 붙이고 클래스에 @RequiredArgsConstructor 를 붙이면
	// 의존 객체를 전달받는 생성자가 자동으로 만들어진다.
	private final MemberMapper mapper;
	
	
	@Override
	public List<MemberDto> getAll() {
		
		return mapper.selectAll();
	}

	@Override
	public MemberDto getMember(int num) {
		MemberDto dto=mapper.getByNum(num);
		// 만일 select 되는 회원정보가 없다면?
		if(dto==null) {
			// 예외 발생시키기
			throw MemberException.notFound(num);
		}
		
		return dto;
	}

	@Override
	public void addMember(MemberDto dto) {
		/*
		 * 	insert 과정에서 SQLException 이 발생하면 자동으로 DataAccessException 이 발생한다
		 * 	dao 에 붙여놓은 @Repository 어노테이션 때문이다.
		 */
		mapper.insert(dto);
	}

	@Override
	public void updateMember(MemberDto dto) {
		int rowCount = mapper.update(dto);
		// 만일 수정되지 않았다면?
		if(rowCount == 0) {
			throw MemberException.updateFailed(dto.getNum());
		}
		
	}

	@Override
	public void deleteMember(int num) {
		int rowCount = mapper.delete(num);
		// 만일 삭제되지 않았다면?
		if(rowCount == 0) {
			throw MemberException.deleteFailed(num);
		}
		
	}

}
