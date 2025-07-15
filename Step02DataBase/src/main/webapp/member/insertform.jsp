<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/member/insertform.jsp</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<h1>회원 정보 추가 양식</h1>
		<form action="${pageContext.request.contextPath }/member/insert.jsp" method="post">
			<div class="mb-2">
				<label class="form-label" for="name">이름</label>
				<input tclass="form-control"
				type="text" name="name" id="name" />
				<div class="form-text">10글자 이내로 입력해 주세요</div>
			</div>
			<div class="mb-2">
				<label for="addr">주소</label>
				<input type="text" name="addr" id="addr" />
				<div class="form-text">실제 거주지를 입력해 주세요</div>
			</div>
			<button class="btn btn-primary btn-sm" fom-type="submit">  
			저 장  
				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-floppy2-fill" viewBox="0 0 16 16">
	  			<path d="M12 2h-2v3h2z"/>
	  			<path d="M1.5 0A1.5 1.5 0 0 0 0 1.5v13A1.5 1.5 0 0 0 1.5 16h13a1.5 1.5 0 0 0 1.5-1.5V2.914a1.5 1.5 0 0 0-.44-1.06L14.147.439A1.5 1.5 0 0 0 13.086 0zM4 6a1 1 0 0 1-1-1V1h10v4a1 1 0 0 1-1 1zM3 9h10a1 1 0 0 1 1 1v5H2v-5a1 1 0 0 1 1-1"/>
				</svg>
			</button>
		</form>
		<button class="btn-primaty;type="button" onclick=location.href="${pageContext.request.contextPath }/member/list.jsp">회원 목록으로 돌아가기</button>
	</div>
</body>
</html>