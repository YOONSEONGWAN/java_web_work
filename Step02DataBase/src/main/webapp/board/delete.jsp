<%@page import="test.dao.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// GET 방식 파라미터로 전달되는 글 번호를 읽어와서
	int num=Integer.parseInt(request.getParameter("num"));
	
	// 글 작성자와 로그인 된 userName 이 동일한지 비교해서 동일하지 않으면 에러를 응답한다.
	String writer=BoardDao.getInstance().getByNum(num).getWriter(); // 삭제할 글 작성자
	String userName=(String)session.getAttribute("userName");
	// 만일 작성자와 userName 로그인 된 userName 하고 같이 않으면
	if(!writer.equals(userName)){
		// 에러 페이지 응답
		response.sendError(HttpServletResponse.SC_FORBIDDEN, " 남의 글 지우지 말아주세요!");
		return;//메소드를 여기서 종료
	}
	
	// DB 에서 해당글을 삭제하고
	BoardDao.getInstance().deleteByNum(num);
	// 응답한다.

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/board/delete.jsp</title>
</head>
<body>
	<script>
		alert("삭제 완료! ");
		location.href="${pageContext.request.contextPath }/board/list.jsp";
	</script>
</body>
</html>