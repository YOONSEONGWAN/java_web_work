<%@page import="test.dao.UserDao"%>
<%@page import="test.dto.UserDto"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	// get 방식 파라미터로 넘어오는 입력 id 를 읽어온다.
	String inputId=request.getParameter("inputId");
	// dao 이용해서 해당 정보 있는지 select 해본다.
	UserDto dto = UserDao.getInstance().getByUserName(inputId); 
	// inputId가 중복이 없으면 db에 없을 거고 dao 에 null 넣어봤자 null dto가 나온다 -> 널이면 사용가능
	// 아이디 사용 가능 여부 알아내기
	boolean canUse = dto == null ? true : false ; 
	
%>
{"canUse": <%=canUse %>}