<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>index.jsp</title>
</head>
<body>
	<h1>인덱스 페이지 입니다.</h1>
	<div class="container">
		<ul>
			<li><a href="${pageContext.request.contextPath }/book/list.jsp">도서 목록 보기 </a></li>
		</ul>
	</div>
</body>
</html>