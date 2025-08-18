<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/WEB-INF/views/book/update.jsp</title>
</head>
<body>
	<div class="container">
		<p>
			<strong>${param.name }</strong>님의 정보를 수정했다.
			<a href="${pageContext.request.contextPath }/book/list">돌아가라.</a>
		</p>
	</div>
</body>
</html>