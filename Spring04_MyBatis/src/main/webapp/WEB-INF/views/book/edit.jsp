<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/WEB-INF/views/book/edit.jsp</title>
</head>
<body>
	<div class="container">
		<h1>회원 수정 폼</h1>
		<form action="${pageContext.request.contextPath }/book/update" method="post">
			<input type="hidden" name="num" value="${dto.num }" />
			<div>
				<label for="name">이름</label>
				<input type="text" name="name" id="name" value="${dto.name }" />
			</div>
			<div>
				<label for="author">저자</label>
				<input type="text" name="author" id="author" value="${dto.author }" />
			</div>
			<div>
				<label for="publisher">출판사</label>
				<input type="text" name="publisher" id="publisher" value="${dto.publisher }" />
			</div>
			<button type="submit">수정 완료하기</button>
			<button type="reset">다시 작성하기</button>
		</form>
	</div>
</body>
</html>