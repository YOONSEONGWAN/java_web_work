package com.example.spring04;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.spring04.dto.MemberDto;
import com.example.spring04.repository.MemberDao;

/*
 * 	여기에 test 코드를 넣고 run As JUnit Test 를 하면 단위테스트가 가능하다.
 */
@SpringBootTest
class Spring04MyBatisApplicationTests {

	@Autowired
	private SqlSession session;
	
	@Autowired
	private MemberDao dao;
	
	@Test
	void contextLoads() {
		Assertions.assertNotNull(session, "SqlSession 은 null 이면 안 됨");
		Assertions.assertNotNull(dao, "MemberDao 는 null 이면 안 됨");
	}
	
	@Test
	void sqlsession_dao_injected() {
		Assertions.assertNotNull(session, "SqlSession 은 null 이면 안 됨");
		Assertions.assertNotNull(dao, "MemberDao 는 null 이면 안 됨");
	}
	
	@Test
	@DisplayName("MemberDao 와 SqlSession 객체 DI 성공 여부 테스트")
	void test01() {
		Assertions.assertNotNull(session, "SqlSession 은 null 이면 안 됨");
		Assertions.assertNotNull(dao, "MemberDao 는 null 이면 안 됨");
	}
	
	@Test
	@DisplayName("member table 에서 회원 목록 selsct 테스트")
	void selectTest() {
		// 회원목록을 select 한다
		List<MemberDto> list=dao.selectAll();
		Assertions.assertNotNull(list, "selectAll() 은 null 이 아니어야 합니다");
	}
}
