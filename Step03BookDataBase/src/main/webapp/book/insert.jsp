<%@page import="test.dao.BookDao"%>
<%@page import="test.dto.BookDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String name=request.getParameter("name");
	String author=request.getParameter("author");
	String publisher=request.getParameter("publisher");
	if (name == null || name.trim().isEmpty() ||
		author == null || author.trim().isEmpty()
		) {
			%>
			    <script>
			        alert("이름과 저자는 반드시 입력해주세요!");
			        history.back(); // 폼 페이지로 되돌아감
			    </script>
			    <%
			        return; // 아래 코드 실행 안 되게 종료!
			    }
	BookDto dto=new BookDto();
	BookDao dao=new BookDao();
	
	dto.setName(name);
	dto.setAuthor(author);
	dto.setPublisher(publisher);
	
	boolean isSuccess=dao.insert(dto);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/book/insert.jsp</title>
</head>
<body>
	<%if(isSuccess){%>
		<p>
			<strong><%=name %></strong> 도서의 정보를 성공적으로 저장했습니다.
		</p>
	<%}else {%> 
		<p>
			회원 정보 저장 실패!
			<a href="${pageContext.request.contextPath }/book/insertform.jsp">다시 입력하기</a>
		</p>
	<%} %>
	<br />
	<a href="${pageContext.request.contextPath }/book/list.jsp">목록에서 확인하기</a>
	
</body>
</html>