<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/board/new-form.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>

<!-- Toast UI Editor CSS -->
<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />

<!-- Toast UI Editor JS -->
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>

<!-- 한국어 번역 파일 추가 -->
<script src="https://uicdn.toast.com/editor/latest/i18n/ko-kr.js"></script>
    
</head>
<body>
	<div class="container">
		<h1>게시글 작성 양식</h1>
		<form action="save.jsp" method="post">
			<div class="mb-2">
				<label class="form-label" for="title">제목</label>
				<input class="form-control" type="text" name="title" id="title" />
			</div>
			<div class="mb-2">
				<label class="form-lable" for="content">제목</label>
				<textarea class="form-control" name="content" id="content" rows="10"></textarea>
			</div>
			<button class="btn btn-success btn-sm" type="submit">저장</button>
			<a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath }/board/list.jsp">취소</a>
		</form>
	</div>
</body>
</html>