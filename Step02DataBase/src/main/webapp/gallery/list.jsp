<%@page import="test.dto.GalleryDto"%>
<%@page import="java.util.List"%>
<%@page import="test.dao.GalleryDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	List<GalleryDto> list=GalleryDao.getInstance().getListWithImages();
	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/gallery/list.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/include/navbar.jsp">
		<jsp:param value="gallery" name="thisPage"/>
	</jsp:include>
	<div class="container">
		<a class="btn btn-outline-primary btn-sm mt-2" href="new-form.jsp">
	       <i class="bi bi-image"></i> 
	       <i class="bi bi-plus"></i>
	       <span class="visually-hidden">갤러리 새글 작성</span> <!-- 시각적으로 안보이게 숨겨놓기(웹 접근성) -->
	    </a>
		<h1>Gallery 목록</h1>
		<div class="row">
			<%for(GalleryDto tmp:list){ 
				// 0번째 대표 이미지의 저장된 파일명 
				String name=tmp.getImageList().get(0).getSaveFileName();
				// 전체 이미지의 개수
				int count = tmp.getImageList().size();
			%>
				<div class="col-sm-6 col-md-4 mb-4">
					<div class="card">
						<img class="card-img-top" src="${pageContext.request.contextPath }/upload/<%=name %>" />
						<div class="card-body">
							<h4><%=tmp.getTitle() %></h4>
							<a class="btn btn-sm btn-success" href="view.jsp?num=<%=tmp.getNum() %>">
								<%=count %> 개의 이미지 
							</a>
						</div>
					</div>
				</div>
			<%} %>
		</div>
	</div>
</body>
</html>