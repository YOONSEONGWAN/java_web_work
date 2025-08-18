<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/WEB-INF/views/book/new-form.jsp</title>
</head>
<body>
	<div class="container">
		<h1>책 추가 양식</h1>
		<form action="${pageContext.request.contextPath }/book/save" method="post">
			<div>
				<label for="name">이름</label>
				<input type="text" name="name" id="name"  />
			</div>
			<div>
				<label for="author">저자</label>
				<input type="text" name="author" id="author"  />
			</div>
			<div>
				<label for="publisher">출판사</label>
				<input type="text" name="publisher" id="publisher"  />
			</div>
			<button type="submit">저장</button>
		</form>
	</div>
</body>
</html>