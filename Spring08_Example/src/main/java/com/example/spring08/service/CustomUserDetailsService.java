package com.example.spring08.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.spring08.dto.UserDto;
import com.example.spring08.repository.UserDao;

import lombok.RequiredArgsConstructor;

/*
 * 	Spring Security 가 로그인 처리시 호출하는 메소드를 가지고 있는 서비스 클래스 정의하기 
 */

@Service // bean 으로 만들기 위한 어노테이션
@RequiredArgsConstructor // 객체주입. final은 필수
public class CustomUserDetailsService implements UserDetailsService {
	
	/*
	 * 	1. 클라이언트가 로그인을 시도하면 이 메소드를 spring security 가 호출한다.
	 * 	2. 메소드를 호출하면서 클라이언트가 입력한 userName 을 전달해준다
	 * 	3. 우리는 전달된 userName 을 이용해서 DB 에서 해당 유저의 정보를 select 해야한다.
	 * 	4. 만일 select 된 유저 정보가 없으면 예외를 발생시키면 되고 
	 * 	5. select 된 정보가 있다면 UserDetails type 객체에 select 된 정보 (비밀번호, 권한 등)를 담아서 리턴
	 * 	6. 이 메소드에서 리턴한 UserDetails 객체를 이용해서 spring security 가 로그인 처리를 대신해준다.
	 */
	// UserDao 객체 주입 받기
	private final UserDao dao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// DB 에서 userName 에 해당하는 정보가 있는지 select 해본다.
		UserDto dto = dao.getByUserName(username) ;
		
		// 만일 존재하지 않는 사용자라면
		if(dto==null) {
			throw new UsernameNotFoundException("존재하지 않는 사용자입니다.");
		}
		
		
		
		//권한 목록을 List 에 담아서  (지금은 1개 이지만)
		List<GrantedAuthority> authList=new ArrayList<>();
		authList.add(new SimpleGrantedAuthority(dto.getRole()));
		
		//UserDetails 객체를 생성해서 
		UserDetails ud=new User(dto.getUserName(), dto.getPassword(), authList);
		//리턴해준다.
		return ud;
	}

	
}
