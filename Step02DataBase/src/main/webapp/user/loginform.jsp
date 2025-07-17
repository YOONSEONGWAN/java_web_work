<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// GET 방식 파라미터 url 이라는 이름으로 전달되는 값이 있는지 읽어와 본다.
	String url=request.getParameter("url");
	// 만일 넘어오는 값이 없다면
	if(url == null){
		// login 후에 인덱스 페이지로 갈 수 있도록 한다.
		String cPath=request.getContextPath();
		url=cPath+"/index.jsp";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/user/login.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
</head>
<body>
	<div class="container py-5" style="max-width:500px;">
		<h1 class="mb-4 text-center">로그인 하기</h1>
		<div class="mb-3 text-center">
			<a href="${pageContext.request.contextPath }" class="btn btn-outline-secondary btn-sm">메인 화면으로</a>
		</div>
		
		<form action="login.jsp" method="post" class="border p-4 rounded shadow-sm bg-light">
			<%-- 로그인 성공 후에 이동할 url 정보를 추가로 form 전송 되도록 한다. --%>
			<input type="hidden" name="url" value="<%=url %>" />
			
			<div class="mb-3">
				<label for="userName" class="form-label">아이디</label>
				<input type="text" name="userName" id="userName" class="form-control" />
			</div>
			
			<div class="mb-3">
				<label for="password" class="form-label">비밀번호</label>
				<input type="password" name="password" id="password" class="form-control" />
			</div>
			
			<div class="d-grid mb-3">
				<button type="submit" class="btn btn-primary">로그인</button>
			</div>
			
			<div class="d-flex justify-content-between">
				<a href="${pageContext.request.contextPath }/users/findid.jsp" class="text-decoration-none">아이디 찾기</a>
				<a href="${pageContext.request.contextPath }/users/findpassword.jsp" class="text-decoration-none">비밀번호 찾기</a>
			</div>
		</form>
	</div>
</body>
</html>
