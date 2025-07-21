<%@page import="java.util.List"%>
<%@page import="test.dao.BoardDao"%>
<%@page import="test.dto.BoardDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	// 기본 페이지 번호는 1로 설정
	int pageNum=1;
	String strPageNum=request.getParameter("pageNum");
	// 전달되는 페이지 번호가 있다면
	if(strPageNum != null){
		// 해당 페이지 번호를 숫자로 변경해서 사용한다.
		pageNum=Integer.parseInt(strPageNum);
	}
	

	// 한 페이지에 몇개씩 표시할 것인지
	final int PAGE_ROW_COUNT=5;
	
	// 하단 페이지를 몇개씩 표시할 것인지
	final int PAGE_DISPLAY_COUNT=5;
	
	// 보여줄 페이지의 시작 ROWNUM
	int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT; // 공차수열
	// 보여줄 페이지의 끝 ROWNUM
	int endRowNum=pageNum*PAGE_ROW_COUNT; // 등비수열
	
	//하단 시작 페이지 번호 (정수를 정수로 나누면 소수점이 버려진 정수가 나옴)
	int startPageNum = 1 + ((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
	//하단 끝 페이지 번호
	int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
	//전체 글의 갯수
	int totalRow=BoardDao.getInstance().getCount();
	//전체 페이지의 갯수 구하기 (double.실수로 나눠야 소숫점의 실수로 나온다.)
	int totalPageCount=(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
	//끝 페이지 번호가 이미 전체 페이지 갯수보다 크게 계산되었다면 잘못된 값이다.
	if(endPageNum > totalPageCount){
		endPageNum=totalPageCount; //보정해 준다. 
	}	
	
	BoardDto dto=new BoardDto();
	dto.setStartRowNum(startRowNum);
	dto.setEndRowNum(endRowNum);
	
	// 해당 row 만 select
	List<BoardDto> list=BoardDao.getInstance().selectPage(dto);

	// 글 목록 읽어오기
	// List<BoardDto> list=BoardDao.getInstance().selectAll();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/board/list.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/include/navbar.jsp">
		<jsp:param value="board" name="thisPage"/>
	</jsp:include>
	<div class="container pt-2">
		<a class="btn btn-outline-primary btn-sm" href="new-form.jsp">
			새 글 작성
			<i class="bi bi-pencil-square"></i>
		</a>
		<h1>게시글 목록입니다.</h1>
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>글 번호</th>
					<th>작성자</th>
					<th>제목</th>
					<th>조회수</th>
					<th>작성일</th>
				</tr>
			</thead>
			<tbody>
				<% for(BoardDto tmp:list){ %>
					<tr>
						<td><%=tmp.getNum() %></td>
						<td><%=tmp.getWriter() %></td>
						<td>
							<a href="${pageContext.request.contextPath }/board/view.jsp?num=<%=tmp.getNum() %>">
							<%=tmp.getTitle() %></a>
						</td>
						<td><%=tmp.getViewCount() %></td>
						<td><%=tmp.getCreatedAt() %></td>
					</tr>
				<%} %>
			</tbody>
		</table>
		
		<ul class="pagination">
			<%if(startPageNum!=1){ %>
				<li calss="page-item">
					<a class=page-link href="list.jsp?pageNum=<%=startPageNum-1%>">&lsaquo;</a>
				</li>
			<%}else{ %>
				
			<%} %>
			
			<% for(int i=startPageNum; i<=endPageNum; i++){%>
				<li calss="page-item">
					<a class="page-link <%=i==pageNum ? "active" : "" %>" href="list.jsp?pageNum=<%=i %>"><%=i %></a>
				</li>
			<%} %>
			<%if(endPageNum < totalPageCount){ %>
				<li calss="page-item">
					<a class=page-link href="list.jsp?pageNum=<%=endPageNum+1%>">&rsaquo;</a>
				</li>
			<%}else{ %>
				
			<%} %>
			
		</ul>
		
	</div>
</body>
</html>