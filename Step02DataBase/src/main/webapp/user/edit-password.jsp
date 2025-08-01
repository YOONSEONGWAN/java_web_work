<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/user/edit-password.jsp</title>
</head>
<body>
	<div class="container">
		<h1>비밀번호 수정 양식</h1>
		<form action="update-password.jsp" method="post" id="editForm">
			<div>
				<label for="password">기존 비밀번호</label>
				<input type="password" name="password" id="password" />
			</div>
			<div>
				<label for="newPassword">새 비밀번호</label>
				<input type="password" name="newPassword" id="newPassword" />
			</div>
			<div>
				<label for="newPassword2">새 비밀번호 확인</label>
				<input type="password" name="newPassword2" id="newPassword2" />
			</div>
			<button type="submit">수정하기</button>
			<br />
			<a href="${pageContext.request.contextPath }">메인 화면으로</a>
		</form>
	</div>
	<script>
		// id 가 editform 인 요소에 "submit" 이벤트가 일어났을 때 실행할 함수 등록
		// form 안에 있는 submit 버튼을 누르면 해당 form 에는 "submit" 이벤트가 발생한다.
		document.querySelector("#editForm").addEventListener("submit", (e)=>{
			/*
				여기서 할 일
				- 폼에 입력한 내용의 유효성 검증
				- 검증해서 유효하다면(잘 입력했다면) 관여하지 않는다(자동으로 폼이 제출된다)
				- 유효하지 않으면 e.preventDefault(); 를 실행하여 폼 제출을 막는다.
			*/
			// 기존 비밀번호
			const pwd=document.querySelector("#password").value;
			// 새 비밀번호
			const newPwd = document.querySelector("#newPassword").value;
			// 새 비밀번호 확인
			const newPwd2 = document.querySelector("#newPassword2").value;
			
			if(pwd.trim() == ""){ // 문자열에서 공백 제거(좌우)해서 비교
				alert("기존 비밀번호를 입력하세요!");
				e.preventDefault();
			}else if(newPwd.trim() == ""){ 
				alert("새 비밀번호를 입력하세요!");
				e.preventDefault();
			}else if(newPwd2.trim() == ""){ 
				alert("새 비밀번호를 확인란을 입력하세요!");
				e.preventDefault();
			}else if(newPwd.trim() !== newPwd2.trim()){ 
				alert("새 비밀번호를 확인란과 동일하게 입력하세요!");
				e.preventDefault();
			}
			
		});
	</script>
</body>
</html>