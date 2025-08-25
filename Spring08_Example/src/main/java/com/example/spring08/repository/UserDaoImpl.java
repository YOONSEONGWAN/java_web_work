package com.example.spring08.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.spring08.dto.UserDto;

import lombok.RequiredArgsConstructor;

// Dao 에는 @Repository 어노테이션을 붙여서 bean 을 만든다. 
// 이렇게 하면 SqlException 이 발생하면 자동으로 DataAccessException 으로 변환ㄷ해서 발생시켜줌 
// 그럼 transaction 관리가 간편해진다.
@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
	
	// lombok 에서 제공해주는 @RequiredArgsConstructor 어노테이션을 이용해서 의존객체를 생성자로 주입 받도록 한다.
	private final SqlSession session;

	@Override
	public void insert(UserDto dto) {
		session.insert("user.insert", dto);
	}

	@Override
	public int update(UserDto dto) {
		return session.update("user.update",dto);
		
	}

	@Override
	public int updatePassword(UserDto dto) {
		return session.update("user.updatePassword", dto);
		
	}

	@Override
	public UserDto getByNum(long num) {
		return session.selectOne("user.getByNum", num);
		
	}

	@Override
	public UserDto getByUserName(String userName) {
		return session.selectOne("user.getByUserName", userName);
		
	}

}
