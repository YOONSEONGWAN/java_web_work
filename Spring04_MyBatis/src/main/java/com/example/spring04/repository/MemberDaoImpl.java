package com.example.spring04.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.spring04.dto.MemberDto;

@Repository
public class MemberDaoImpl implements MemberDao{

	// MyBatis 를 사용할 때 필요한 핵!심객체
	@Autowired
	private SqlSession session;
	
	// 생성자를 이용해서 의존 객체를 주입받는 게 더 일반적이다.(lombok 의 기능을 이용하면 생략이 가능하다.)
	// @Autowired // 생성자가 오직 1개인 경우에는 생략이 가능하다.
	public MemberDaoImpl(SqlSession session) {
		this.session=session;
	}
	
	@Override
	public void insert(MemberDto dto) {
		session.insert("member.insert", dto);
		
	}
	
	@Override
	public int update(MemberDto dto) {
		// 이 메소드는 정수값을 리턴해준다. 업데이트된 로우의 갯수를 리턴해줌 
		return session.update("member.update", dto); 
	}
	
	@Override
	public int deleteByNum(int num) {
		// 이 메소드는 정수값을 리턴해준다. 삭제된 로우의 갯수를 리턴해줌 
		return session.delete("member.delete", num);
	}
	
	@Override
	public List<MemberDto> selectAll() {
		/*
		 * 	.selectList() 를 호출하면 리턴 type 은 무조건 List<T> 이다.
		 * 	List 의 generic type T 는 그때그때 다르다
		 * 	resultType 이 바로 List 의 generic type 으로 설정된다.
		 */
		List<MemberDto> list=session.selectList("member.selectAll");
		return list;
	}

	/*
	 * 	select 되는 row 가 한 개면 session.selectOne() 메소드 사용
	 * 	select 되는 row 가 여러 개면 session.selectList() 메소드를 사용해서 select 한다. 
	 */
	@Override
	public MemberDto getByNum(int num) {
		MemberDto dto=session.selectOne("member.getByNum", num); // 리턴되는 데이터의 타입은 동적이다.
		return dto;
	}
}
