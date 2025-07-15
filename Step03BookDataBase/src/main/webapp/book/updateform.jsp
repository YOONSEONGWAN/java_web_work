<%@page import="test.dao.BookDao"%>
<%@page import="test.dto.BookDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    // 수정할 책의 번호
    int num=Integer.parseInt(request.getParameter("num"));
    // 수정할 책의 정보를 db에서 불러온다.
    BookDto dto=new BookDao().getByNum(num);
    // 책 수정 양식을 응답한다.
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/book/updateform.jsp</title>
</head>
<body>
	<div class="container">
		<h1>도서 정보 수정하는 양식</h1>
		<form action="${pageContext.request.contextPath }/book/update.jsp" method="post">
			<div>
				<label for="num">번호</label>
				<input type="text" name="num" value="<%=dto.getNum() %>" readonly />
			</div>
			<div>
				<label for="name">이름</label>
				<input type="text" name="name" id="name" value="<%=dto.getName() %>" />
			</div>
			<div>
				<label for="author">저자</label>
				<input type="text" name="author" id="author" value="<%=dto.getAuthor() %>" />
			</div>
			<div>
				<label for="publisher">출판사</label>
				<input type="text" name="publisher" id="publisher" value="<%=dto.getPublisher() %>" />
			</div>
			<button type="submit">수정하기</button>
			<button type="reset">취소하기</button>
			<br />
			<a href="${pageContext.request.contextPath }/book/list.jsp">목록으로 돌아가기</a>			
		</form>
	</div>
</body>
</html>