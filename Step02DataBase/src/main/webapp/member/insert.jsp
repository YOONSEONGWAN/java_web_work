<%@page import="java.util.List"%>
<%@page import="test.dto.MemberDto"%>
<%@page import="test.dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//파라미터가 넘어오는 이름 기억하기 
	String name=request.getParameter("name");
	String addr=request.getParameter("addr");
	 if (name == null || name.trim().isEmpty() ||
		 addr == null || addr.trim().isEmpty()) {
		%>
		    <script>
		        alert("이름과 주소를 모두 입력해주세요!");
		        history.back(); // 폼 페이지로 되돌아감
		    </script>
		    <%
		        return; // 아래 코드 실행 안 되게 종료!
		    }
	MemberDto dto=new MemberDto();
	MemberDao dao=new MemberDao();
	
	dto.setName(name);
	dto.setAddr(addr);
	
	boolean isSuccess=dao.insert(dto);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/member/insert.jsp</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
</head>
<body>
	<div class="container">
		<%if(isSuccess){%>
			<p class="alert alert-success mt-5">
			<i class="bi bi-check-circle-fill"></i>
				<strong><%=name %></strong>님의 정보를 성공적으로 저장했습니다.
				<a class="laert-link" href="${pageContext.request.contextPath }/member/list.jsp">목록에서 확인하기</a>
			</p>
		<%}else {%> 
			<p class="alert alert-danger mt-5">
			<i class="bi bi-x-circle-fill"></i>
				회원 정보 저장 실패!
				<a class="alert-link" href="${pageContext.request.contextPath }/member/insertform.jsp">다시 입력하기</a>
			</p>
		<%} %>
	</div>
	
</body>
</html>