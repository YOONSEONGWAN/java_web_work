package com.example.spring10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

// resources 에 작성한 custom.properties 파일을 로딩하도록 한다
@PropertySource(value="classpath:custom.properties")
@SpringBootApplication
public class Spring10JwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring10JwtApplication.class, args);
	}

}
