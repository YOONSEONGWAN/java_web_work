package com.example.spring05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class Spring05ThymeleafApplication {

	// 이 클래스로 객체가 생성된 직후 호출될 메소드에 붙이는 어노테이션
	@PostConstruct
	public void test() {
		// @data 어노테이션이 붙은 MemberDto 클래스로 테스트
		MemberDto dto=new MemberDto();
		dto.setNum(1);
		dto.setName("김구라");
		dto.setAddr("노량진");
		// @Data 어노테이션이 toString 메소드를 재정의 하기 때문에 객체의 필드 안에 들어있는 내용 확인 가능 
		System.out.println(dto);
		
		// @Builder 의 기능을 이용해서 MemberDto 객체 얻어내기
		MemberDto dto2 = MemberDto.builder()
				.num(2)
				.name("해골")
				.addr("행신동").build();
		System.out.println(dto2);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Spring05ThymeleafApplication.class, args);
	}

}
