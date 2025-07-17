<%@page import="java.net.URLEncoder"%>
<%@page import="test.dao.UserDao"%>
<%@page import="test.dto.UserDto"%>
<%@page import="org.mindrot.jbcrypt.BCrypt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 폼 전송되는 아이디와 비밀번호 추출하기
	String userName=request.getParameter("userName");
	String password=request.getParameter("password");
	// login 후에 가야할 목적지 정보
	String url=request.getParameter("url");
	// login 실패를 대비해서 목적지 정보를 인코딩 한 결과도 준비한다. 다시 로그인 폼으로 돌아감
	String encodedUrl=URLEncoder.encode(url, "UTF-8");
	
	// 아이디 비밀번호가 유효한 정보인지 여부(초기 false값 준다)
	boolean isValid=false;
	// DB 에서 userName 을 이용해서 select 되는 정보가 있는지 확인하기 위해 SELECT
	UserDto dto=UserDao.getInstance().getByUserName(userName);
	// 만일 select 된 정보가 있다면 (최소한 유저네임은 존재한다는 것)
	if(dto != null){
		// raw 비밀번호와 DB 에 저장된 암호화된 비밀번호를 비교해서 일치여부 확인
		isValid=BCrypt.checkpw(password, dto.getPassword());
	}
	/* 	만일 입력한 아이디와 비밀번호가 유효한 정보라면 로그인 처리를 한다.
		jsp 에서 기본 제공해주는 HttpSession 객체에 userName 저장한다
		 HttpSession 객체에 저장하면 응답을 한 이후에 다음번 요청에서 
		 HttpSession 객체에 저장된 정보를 읽어올 수 있다.
		세선 객체에 담긴 정보는 어떤 요청도 하지 않고 일정 시간이 흐르면 자동 삭제 
		필요하다면(로그아웃이 요청되면) 강제로 세션 객체에 담긴 정보를 삭제할 수도 있다.
		세션 객체에 담긴 로그인 정보를 삭제하는 것이 "로그아웃"이고,
		세션 객체에 로그인 정보(userName)를 저장하는 것이 "로그인"이다.
		요청하는 클라이언트만큼 독립된 세션 객체가 있다.
	*/
	if(isValid){
		// HttpSesion 객체에 "userName" 이라는 키값으로 userName 을 저장한다.
		session.setAttribute("userName", userName); // 유저네임, 키값
		// session 유지시간 설정(초단위) 
		session.setMaxInactiveInterval(60*60); // drfault는 30분 (서버에 요청 기준)
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/users/login.jsp</title>
</head>
<body>
	<div class="container">
		<%if(isValid){ %>
		<p>
			<strong><%=userName %></strong>회원님 반갑습니다.
			<a href="${pageContext.request.contextPath }/">인덱스 페이지로</a>
			<a href="<%=url%>">확인</a>
		</p>
		<% }else{ %>
			<p>
				아이디 혹은 비밀번호가 틀렸습니다.
				<a href="loginform.jsp?url=<%=encodedUrl%>">다시 로그인</a>
				<a href="${pageContext.request.contextPath }">메인 화면으로</a>
			</p>
			<%} %>
	</div>
</body>
</html>