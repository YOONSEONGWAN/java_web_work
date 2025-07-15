<%@page import="test.dao.MemberDao"%>
<%@page import="test.dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 1. 업데이트한 넘버 파라미터 읽어오기
	int num=Integer.parseInt(request.getParameter("num"));
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
	dto.setNum(num);
	dto.setName(name);
	dto.setAddr(addr);
	
	MemberDao dao=new MemberDao();
	
	boolean isSuccess = dao.update(dto);

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/member/update.jsp</title>
</head>
<body>
	<%if(isSuccess){%>
		<p>
			<strong><%=name %></strong>님의 정보를 성공적으로 저장했습니다.
		</p>
	<%}else {%> 
		<p>
			회원 정보 저장 실패!
			<a href="updateform.jsp?num=<%=num%>">다시 입력하기</a>
		</p>
		<%} %>
		<br />
		<a href="list.jsp">목록에서 확인하기</a>
</body>
</html>