<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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