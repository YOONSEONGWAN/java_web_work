<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/WEB-INF/views/book/delete.jsp</title>
</head>
<body>
	<script>
		alert("${param.num}번 책의 정보를 성공적으로 삭제했어요 !")
		location.href="${pageContext.request.contextPath }/book/list"
	</script>
</body>
</html>