<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/user/signup-form.jsp</title>
</head>
<body>
	<div class="container">
		<h1>회원가입 양식</h1>
		<form action="action.jsp" method="post">
			<div>
				<label for="id">아이디</label>
				<input type="text" name="id" id="id" />
			</div>
			<div>
				<label for="passward">비밀번호</label>
				<input type="text" name="passward" id="passward" />
			</div>
			<div>
				<label for="email">이메일</label>
				<input type="email" name="email" id="email" />
			</div>
			<button type="submit">가입</button>
		</form>
	</div>
</body>
</html>