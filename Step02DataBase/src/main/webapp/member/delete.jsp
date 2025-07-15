<%@page import="test.dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 1. 파라미터 받아오기
	String numStr=request.getParameter("num");
	int num=Integer.parseInt(numStr);
	
	MemberDao dao=new MemberDao();
	// 삭제할 회원 정보 삭제
	boolean isSuccess = dao.deleteByNum(num);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/member/delete.jsp</title>
</head>
<body>
	<!-- javascript 를 응답해서 알림창이 뜨고 확인 버튼을 누르면 다시 목록 보기로 이동하도록 한다.  -->
	<script>
		<% if(isSuccess) { %>
			alert("회원 정보를 삭제했어요!");
		<% } else { %>
			alert("삭제 실패 ㅠㅠ");
		<% } %>
		
	</script>
	<a href="${pageContext.request.contextPath }/member/list.jsp">목록으로 돌아가기</a>
</body>
</html>