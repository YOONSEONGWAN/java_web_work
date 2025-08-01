<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String name = request.getParameter("name");
	String gender=request.getParameter("gender");
	String intro=request.getParameter("intro");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>preview.jsp</title>
</head>
<body>
	<form >
		<label>이름:</label>
		<input type="text" name="name" vlaue="<%=name %>"><br><br>
		
		<label>성별: </label>
		<select name="gender" vlaue="<%=gender %>">
		  <option value="남">남</option>
		  <option value="여">여</option>
		</select><br><br>
		
		<label>자기소개: </label><br>
		<textarea name="intro" rows="4" cols="40"><%=intro %>
		</textarea>
	</form>
</body>
</html>