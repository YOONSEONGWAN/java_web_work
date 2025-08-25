package com.example.spring08.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 설정에 관련된 bean 이라고 알려준다.
@EnableWebSecurity // Spring Security 를 설정하기 위한 어노테이션
public class SecurityConfig {

   
	
	/* 
	 * 	*bean 어노테이션에 대한 이해*
	 * 
	 * 	메소드에 @Bean 어노테이션을 붙여 놓으면 
	 * 	1. spring 이 자동으로 해당 메소드를 호출하고
	 * 	2. 매개변수에 필요한 type 객체가 있으면 알아서 spring bean container 에서 해당 type 객체를 찾아서
	 * 	전달해준다.
	 * 	3. 또한 메소드가 리턴해주는 객체를 spring bean container 에서 관리해준다.  
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		// security 를 통과하는 white list 설정
		String [] whiteList = {"/", "/user/loginform", "/user/login-fail", 
							 "/user/expired", "/user/signup-form", "/user/signup", 
							 "/test/**", "/user/check-id", "/board/list", "board/view"};
		
		// 下.함수식. 화살표 다음에 있는 코드들은 리턴할 값을 의미 
		
		httpSecurity
		.csrf(csrf->csrf.disable()) // csrf 검증 사용하지 않기
		.authorizeHttpRequests(config->
			config
			.requestMatchers(whiteList).permitAll() // whiteList 는 무조건 허용하기
			.requestMatchers("/admin/**").hasRole("ADMIN") // admin 하위 요청은 무조건 admin 이어야 하고
			.requestMatchers("/staff/**").hasAnyRole("ADMIN", "STAFF") // staff 하위 요청은 admin, staff 롤이어야 한다.
			.anyRequest().authenticated() // 다른 모든 요청은 모두 인증 진행하기 
		)
		.formLogin(config ->
			config
				// 인증을 거치지 않은 사용자를 리다일렉트 시킬 경로 설정 
				.loginPage("/user/required-loginform") // 로그인 페이지 위치
				// 로그인 폼의 action (로그인 처리는 spring security 가 대신 해줌
				.loginProcessingUrl("/user/login") // 프로세스 진행 위치
				// 로그인 폼의 사용자명 입력란 name 속성의 값 <input type="text" name="userName"?
				.usernameParameter("userName") // 파라미터값
				// 로그인 폼의 비밀번호 입력란 name 속성의 값 <input type="text" name="password"?
				.passwordParameter("password")
				//.successForwardUrl("/user/login-success") // 성공, 실패시 포워드 될 url
				// 로그인 성공시 동작하는 핸들러 객체 등록하기 
				.successHandler(new AuthSuccessHandler())
				.failureForwardUrl("/user/login-fail")
				.permitAll() // 위의 요청은 whiteList 에 없어도 요청 가능하도록 설정
		) // 이 요청을 처리할 컨트롤러와 뷰페이지를 만들면 된다. 
		.logout(config ->
			config
				// 로그아웃시 요청할 url (spring security 가 대신 처리해준다)
				.logoutUrl("/user/logout")
				// 로그아웃 후에 리다일렉트 이동할 url 
				.logoutSuccessUrl("/")
				.permitAll()
		)
		.exceptionHandling(config ->
				// 권한 부족시 forward 이동될 url
			config
				.accessDeniedPage("/user/denied")
		)
		.sessionManagement(config ->
				// 최대 세션 허용 갯수 설정 (다중 기기 로그인 허용 여부)
			config
				.maximumSessions(1)
				// 로그인이 해제 되었을 때 응답할 url
				.expiredUrl("/user/expired")
		);
		
		return httpSecurity.build();
	}
	//비밀번호를 암호화 해주는 객체를 bean 으로 만든다.
	@Bean
	PasswordEncoder passwordEncoder() { // 비밀번호를 암호화 해주는 객체
		//여기서 리턴해주는 객체도 bean 으로 된다.
		return new BCryptPasswordEncoder();
	}
	//인증 메니저 객체를 bean 으로 만든다. (Spring Security 가 자동 로그인 처리할때도 사용되는 객체)
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http,
			BCryptPasswordEncoder encoder, UserDetailsService service) throws Exception{
		//적절한 설정을한 인증 메니저 객체를 리턴해주면 bean 이 되어서 Spring Security 가 사용한다 
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(service)
				.passwordEncoder(encoder)
				.and()
				.build();
	}
}
