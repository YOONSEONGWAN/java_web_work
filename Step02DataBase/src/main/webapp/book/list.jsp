<%@page import="test.dto.BookDto"%>
<%@page import="java.util.List"%>
<%@page import="test.dao.BookDao"%>
<%@page import="test.util.DbcpBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	new DbcpBean().getConn();
	BookDao dao=new BookDao();
	List<BookDto> list=dao.selectAll();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/book/list.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
</head>
<style>
    table {
      width: 80%;
      border-collapse: collapse;
      margin: 20px auto;
    }
    th, td {
      border: 1px solid #ccc;
      padding: 10px;
      text-align: center;
    }
    th {
      background-color: #f5f5f5;
    }
    tr:nth-child(even) {
      background-color: #f9f9f9;
    }
  </style>
<body>
	<jsp:include page="/WEB-INF/include/navbar.jsp">
		<jsp:param value="book" name="thisPage"/>
	</jsp:include>
	<div class="container">
		<a href="${pageContext.request.contextPath }">메인 화면으로</a>
		<a href="${pageContext.request.contextPath }/book/insertform.jsp">도서 추가</a>
		<h1 style="text-align:center;">등록 도서 목록</h1>

		<table class="table table-bordered">
		  <thead class="table-dark">
		    <tr>
		      <th>번호</th>
		      <th>제목</th>
		      <th>저자</th>
		      <th>출판사</th>
		      <th>수정</th>
		      <th>삭제</th>
		    </tr>
		  </thead>
			  <tbody>
			    <%
			      for(BookDto tmp : list) {
			    %>
			    <tr>
			      <td><%= tmp.getNum() %></td>
			      <td><%= tmp.getName() %></td>
			      <td><%= tmp.getAuthor() %></td>
			      <td><%= tmp.getPublisher() %></td>
			      <td><a href="updateform.jsp?num=<%=tmp.getNum()%>">수정</a></td>
			      <td>
				<a href="${pageContext.request.contextPath }/book/delete.jsp?num=<%= tmp.getNum() %>" 
				   onclick="return confirm('정말 삭제할까요?');">삭제</a>
			</td>
			    </tr>
			    <%
			      }
			    %>
			  </tbody>
		</table>
	</div>
	<jsp:include page="/WEB-INF/include/footer.jsp"></jsp:include>
</body>
</html>