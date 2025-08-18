<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/WEB-INF/views/book/list.jsp</title>
</head>
<body>
	<div class="container">
		<a href="${pageContext.request.contextPath }/book/new-form">책 추가하기</a>
		<h1>책 목록입니다</h1>
		<table>
			<thead>
				<tr>
					<th>번호</th>
					<th>이름</th>
					<th>저자</th>
					<th>출판사</th>
					<th>수정</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="tmp" items="${list }">
					<tr>
						<td>${tmp.getNum() }</td>
						<td>${tmp.name }</td>
						<td>${tmp.author }</td>
						<td>${tmp.publisher }</td>
						<td><a href="edit?num=${tmp.num }">수정하기</a></td>
						<td><a href="delete?num=${tmp.num }">삭제하기</a></td>
					</tr>
				</c:forEach>	
			</tbody>
		</table>
	</div>
</body>
</html>