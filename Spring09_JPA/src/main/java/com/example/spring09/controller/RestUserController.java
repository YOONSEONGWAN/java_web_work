package com.example.spring09.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring09.dto.LoginRequest;

import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/v1")
@RestController
@Tag(name = "userToken", description = "토큰 발급 관리 API")
public class RestUserController {

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest request ){
		// 입력한 userName 과 password 가 유효한 정보인지 여부
		boolean isValid = request.getUserName().equals("kimgura") && request.getPassword().equals("1234");
		// 유요하지 않다면
		if(!isValid) {
			// 401 응답
			ResponseEntity<String> entity=
					ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 혹은 비밀번호가 틀려요");
			return entity;
		}
		// 가상의 토큰
		String token = "api 자유 이용권, 유효기간: 2025.9.16., userName:"+request.getUserName();
		// 정상응답 하면서 token 발급한다.
		ResponseEntity<String> entity=ResponseEntity.ok(token);
		return entity;
	}
}
