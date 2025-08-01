<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% 
	String orgFileName=(String)request.getAttribute("orgFileName");
	String saveFileName=(String)request.getAttribute("saveFileName");
	long fileSize=(long)request.getAttribute("fileSize");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/test/윤제.jsp</title>
</head>
<body>
	<div class="container">
		<p>윤제가 응답합니다</p>
		<p>원본 파일명: <%=orgFileName %></p>
		<p>저장된 파일명: <%=saveFileName %></p>
		<p>파일크기: <%=fileSize %></p>
		
		
		<!-- el 을 이용하면 casting 없이 바로 추출도 가능 -->
		<p>원본 파일명: ${requestScope.orgFileName }</p>
		<p>저장된 파일명: ${requestScope.saveFileName }</p>
		<!-- requestScope. 생략 가능 -->
		<p>파일의 크기: ${fileSize }</p>
		
		<a href="${pageContext.request.contextPath }/test/download?orgFileName=${orgFileName}&saveFileName=${saveFileName}&fileSize=${fileSize}">다운로드</a>
	</div>
</body>
</html>