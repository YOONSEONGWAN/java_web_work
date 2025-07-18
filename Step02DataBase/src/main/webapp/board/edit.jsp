<%@page import="test.dao.BoardDao"%>
<%@page import="test.dto.BoardDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 수정 할 글의 정보를 얻어와서 
	int num = Integer.parseInt(request.getParameter("num"));
	BoardDto dto=BoardDao.getInstance().getByNum(num);
	// 글 수정 폼을 응답한다.
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/board/edit.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
</head>
<body>
	<div class="container pt-3">
		<nav>
		  <ol class="breadcrumb">
		    <li class="breadcrumb-item">
		    	<a href="${pageContext.request.contextPath }/">Home</a>
	    	</li>
		    <li class="breadcrumb-item">
		    	<a href="${pageContext.request.contextPath }/board/list.jsp">Board</a>
	    	</li>
		    <li class="breadcrumb-item active" >Edit</li>
		  </ol>
		</nav>
		<h1>글 수정 페이지</h1>
		<form action="update.jsp" method="post">
			<div>
				<label for="num" class="form-label">글 번호</label>
				<input type="text" class="form-control" name="num" id="num"
				 value="<%=dto.getNum() %>" readonly/>
			</div>
			<div>
				<label for="writer" class="form-label">작성자</label>
				<input type="text" class="form-control" name="writer" id="witer"
				 value="<%=dto.getWriter() %>" readonly/>
			</div>
			<div>
				<label for="title" class="form-label">제목</label>
				<input type="text" class="form-control" name="title" id="witer"
					value="<%=dto.getTitle() %>" />
			</div>
			<div>
				<label for="content" class="form-label">제목</label>
				<textarea type="text" class="form-control" name="content" id="content"
					rows="10"><%=dto.getContent() %> </textarea>
			</div>
			<button class="btn btn-success btn-sm">수정 확인</button>
			<button class="btn btn-danger btn-sm">리셋</button>
		</form>
	</div>
</body>
</html>