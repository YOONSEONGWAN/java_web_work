package com.example.spring04;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.spring04.controller.HomeController;

// HomeComtroller 를 테스트하기 위한 클래스
@WebMvcTest(HomeController.class )
public class HomeControllerTest {
	
	// 가짜 Mvc 객체를 주입 받는다.
	// 가상의 get, post 등의 요청을 하며 테스트를 할 수 있다. 
	@Autowired
	private MockMvc mvc;
	
	@Test
	@DisplayName("/ 요청시 view page 와 모델 데이터 확인")
	void testHome() throws Exception {
		/*
		 * 	GET 방식 "/" 요청을 수행했을 때
		 * 	1. 정상응답(200)이어야 하고
		 * 	2. view page 의 이름이 home 이어야 하고
		 * 	3. Model 객체에 "notice" 라는 키값으로 담은 내용이 있어야
		 * 	이 테스트가 통과된다.
		 */
		mvc.perform(get("/"))
			.andExpect(status().isOk()) 
			.andExpect(view().name("home"))
			.andExpect(model().attributeExists("notice"));
	}

}
