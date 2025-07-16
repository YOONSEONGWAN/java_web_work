<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// GET 방식 파라미터 url 이라는 이름으로 전달되는 값이 있는지 읽어와 본다.
	String url=request.getParameter("url");
	// 만일 넘어오는 값이 없다면
	if(url == null){
		// login 후에 인덱스 페이지로 갈 수 있도록 한다.
		String cPath=request.getContextPath();
		url=cPath+"/index.jsp";
	}
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/user/login.jsp</title>
</head>
<body>
	<div class="container">
		<h1>로그인 하기</h1>
		<a href="${pageContext.request.contextPath }">메인 화면으로</a>
		<form action="login.jsp" method="post">
			<%-- 로그인 성공 후에 이동할 url 정보를 추가로 form 전송 되도록 한다. --%>
			<input type="hidden" name="url" value="<%=url %>" />
			<div>
				<label for="userName">아이디  </label>
				<input type="text" name="userName" id="userName" />
			</div>
			<div>
				<label for="password">비밀번호</label>
				<input type="password" name="password" id="password" />
			</div>
			<button type="submit">로그인</button>
			<br />
			<a href="${pageContext.request.contextPath }/users/findid.jsp">아이디 찾기</a>
			<a href="${pageContext.request.contextPath }/users/findpassword.jsp">비밀번호 찾기</a>
		</form>
	</div>
</body>
</html>