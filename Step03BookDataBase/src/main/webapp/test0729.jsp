<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test0729.jsp</title>
</head>
<body>
	<form action="preview.jsp" method="post">
		<label>이름: </label>
		<input type="text" name="name"><br><br>
		
		<label>성별: </label>
		<select name="gender">
		  <option value="남">남</option>
		  <option value="여">여</option>
		</select><br><br>
		
		<label>자기소개: </label><br>
		<textarea name="intro" rows="4" cols="40">
	</textarea></form>
</body>
</html>