package com.example.spring10.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring10.dto.PwdChangeRequest;
import com.example.spring10.dto.UserDto;
import com.example.spring10.service.UserService;
import com.example.spring10.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RequestMapping("/v1")
@RestController
@RequiredArgsConstructor
public class UserController {
	
	// 의존 객체 생성자 주입 
	public final JwtUtil jwtUtil;
	//SecurityConfig 클래스에서 Bean 이된 AuthenticationManager 객체 주입받기 
	public final AuthenticationManager authManager;
	
	public final UserService userService;
	
	/*
	 * 	요청 처리 후, 특별히 응답할 내용이 없을 때 204 응답을 하면 된다.
	 */
	@PatchMapping("/user/password")
	public ResponseEntity<Void> passwordUpdate(@RequestBody PwdChangeRequest request) {
		// spring security context 로부터 로그인된 userName 얻어오기
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		request.setUserName(userName);
		
		userService.updatePassword(request);
		
		// 빈 content 와 함께 204 응답이 된다.
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/user")
	public UserDto user() {
		// spring security context 로부터 로그인된 userName 을 얻어낸다.
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		return userService.getUser(userName);
	}
	
	
	@GetMapping("/ping")
	public String ping() {
		
		return "pong";
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserDto dto){
		Authentication authentication=null;
		try {
			UsernamePasswordAuthenticationToken authToken=
					new UsernamePasswordAuthenticationToken(dto.getUserName(), dto.getPassword());
			//인증 메니저 객체를 이용해서 인증을 진행한다. 내부적으로 CustomDetailsService 객체가 활용된다. 
			authentication=authManager.authenticate(authToken);
		}catch(Exception e) {
			//예외가 발생하면 인증실패(아이디 혹은 비밀번호 틀림 등등...)
			e.printStackTrace();
			// 401 UNAUTHORIZED 에러를 응답하면서 문자열 한줄 보내기 
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패!");
		}
		
		//Authentication 객체에는 인증된 사용자 정보가 들어 있다. userName, role 등등 
		//현재는 role 을 하나만 부여하기 때문에 0 번 방에 있는 데이터만 불러오면 된다. 
		GrantedAuthority authority=authentication.getAuthorities().stream().toList().get(0);
		//ROLE_XXX 형식
		String role=authority.getAuthority();
		//"role" 이라는 키값으로 Map 에 담기 
		Map<String, Object> claims=Map.of("role", role);

		//예외가 발생하지 않고 여기까지 실행 된다면 인증을 통과 한 것이다. 토큰을 발급해서 응답한다.
		String token=jwtUtil.generateToken(dto.getUserName(), claims);
		//발급받은 토큰 문자열을 ResponseEntity 에 담아서 리턴한다.
		return ResponseEntity.ok("Bearer "+token);
	}
}	