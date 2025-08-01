<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/test/file-form.jsp</title>
</head>
<body>
	<div class="container">
		<h1>파일 업로드 테스터</h1>
		<form action="${pageContext.request.contextPath}/test/fileup"
			enctype="multipart/form-data" method="post">
			<input type="text" name="caption" placeholder="설명 입력 ..." />
			<br />
			<input type="file" name="myFile" />
			<br />
			<button type="submit">업로드</button>
		</form>
	</div>
</body>
</html>