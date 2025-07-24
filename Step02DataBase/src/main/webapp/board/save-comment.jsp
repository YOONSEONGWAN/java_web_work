<%@page import="test.dto.CommentDto"%>
<%@page import="test.dao.CommentDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	// 폼 전송되는 댓글의 정보를 얻어낸다
	int parentNum=Integer.parseInt(request.getParameter("parentNum"));
	String targetWriter=request.getParameter("targetWriter");
	String content=request.getParameter("content");
	// 댓글 작성자 정보는 session 에서 얻어낸다
	String writer=(String)session.getAttribute("userName");
	// 저장할 댓글의 글 번호를 미리 얻어내야 한다. 
	int num = CommentDao.getInstance().getSequence();
	// 저장할 댓글 정보를 Dto 에 담고
	CommentDto dto=new CommentDto();
	dto.setNum(num);
	dto.setWriter(writer);
	dto.setTargetWriter(targetWriter);
	dto.setContent(content);
	dto.setParentNum(parentNum); // parentNum 은 원글의 글번호였다. 
	dto.setGroupNum(num); // 원글의 댓글은 자신의 글번호가 대댓글의 글번호이다. 
	// 저장하기 전에 글번호가 미리 필요하기 때문에 dao 로 글번호를 얻어내어 dto 에 담았다.
	/************************/
	// DB에 저장하고
	boolean isSuccess=CommentDao.getInstance().insert(dto);
	// 응답한다. 새 경로로 이동하라는 리다일렉트 응답
	
	String cPath=request.getContextPath();
	// 원래글 자세히 보기 페이지로 이동
	response.sendRedirect(cPath+"/board/view.jsp?num="+parentNum);
	
%>