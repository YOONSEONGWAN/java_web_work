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
		// role 도 세션에 담는다.
		session.setAttribute("role", dto.getRole());
		// session 유지시간 설정(초단위) 
		session.setMaxInactiveInterval(60*60); // drfault는 30분 (서버에 요청 기준)
	}
	
	// checkbox 를 체크한 상태로 로그인 버튼을 누르면 null 이 아니다 (체크 안 하면 null)
	String isSave=request.getParameter("isSave");
	if(isSave!=null){
		// 입력한 아이디 비밀번호를 쿠키로 응답하고 1주일동안 유지되도록 한다.
		Cookie cook1=new Cookie("savedUserName", userName);
		Cookie cook2=new Cookie("savedPassword", password);
		// cookie 유지시간 초단위로 설정
		cook1.setMaxAge(60*60*24*7);
		cook2.setMaxAge(60*60*24*7);
		// cookie 경로
		//cook1.setPath("/");// 모든 경로에서 사용할 수 있는 쿠키
		//cook1.setPath("/user/"); // user 경로에서만 사용할 수 있는 쿠키
		// 설정하지 않으면 현재 경로에서만 사용할 수 있음
		
		// HttpServletResponse 객체에 Cookie 객체를 담으면 응답할 때 알아서 쿠키가 응답됨.
		response.addCookie(cook1);
		response.addCookie(cook2);
	}else{
		// 체크박스 해제 했으면 쿠키 삭제
		Cookie cook1=new Cookie("savedUserName", "");
		Cookie cook2=new Cookie("savedPassword", null);
		// 쿠키 유지시간을 0으로 설정해서 응답하면 쿠키가 삭제되는 효과
		cook1.setMaxAge(0);
		cook2.setMaxAge(0);
		response.addCookie(cook1);
		response.addCookie(cook2);
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