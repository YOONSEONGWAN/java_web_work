<%@page import="test.dao.CommentDao"%>
<%@page import="test.dto.CommentDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 삭제할 댓글 번호
	int num=Integer.parseInt(request.getParameter("num"));
	// 리다일렉트 이동할 때 필요한 원글의 글번호 (주소이기에 숫자로 바꿀 필요 없다.)
	String parentNum=request.getParameter("parentNum");
	// dao 객체 이용해 삭제하고
	CommentDao.getInstance().delete(num);
	
	// 리다일렉트 이동. 삭제되면 delete.jsp로 갔다가 순간적으로 원글로 돌아옴
	String cPath=request.getContextPath();
	response.sendRedirect(cPath+"/board/view.jsp?num="+parentNum);

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/board/comment-delete.jsp</title>
</head>
<body>

</body>
</html>