<%@page import="test.dao.UserDao"%>
<%@page import="test.dto.UserDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 1. 기존 이메일과 프로필 사진을 먼저 나타내야 함
	
	// 2. 수정 이메일 입력칸 생성
	
	// 3. 프로필 등록 가능하게 파일 불러오는 input 필요
	// ---------------------------------------------
	
	String userName=(String)session.getAttribute("userName");
	UserDto dto=UserDao.getInstance().getByUserName(userName);
	


%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/user/edit.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
</head>
<body>
	<div class="container">
		<h1>가입정보 수정 양식</h1>
		<!-- 
			input type="file" 이 있는 form 의 전송 방식은 다르다.
			따라서 enctype="multipart/form-data" 속성을 form에 추가한다.
			서버에서 해당 요청을 처리하는 방법도 다르기 때문에 jsp 가 아닌 서블릿에서 처리하자.
		 -->
		<form action="${pageContext.request.contextPath }/user/update" method="post" 
			enctype="multipart/form-data">
			<div>
				<label for="userName">아이디</label>
				<input type="text" name="userName" value="<%=dto.getUserName() %>" readonly />
			</div>
			<div>
				<label for="email">이메일</label>
				<input type="text" name="email" value="<%=dto.getEmail()%>"/>
			</div>
			<div>
				<label>프로필 이미지</label>
				<div>
					<a href="javascript:" href="javascript:" id="profileLink">
					<%if(dto.getProfileImage() == null){ %>
						<i style="font-size:100px;" " class="bi bi-person-circle"></i>
					<%}else{ %>
						<img src="${pageContext.request.contextPath }/upload/<%=dto.getProfileImage() %>"
							style="width:100px; height:100px; border-radius:50%;"  />
					<%} %>
					</a>
				</div>
				<input type="file" name="profileImage" accept="image/*" style="display:none;" />
			</div>
			<button type="submit">수정확인</button>
			<button type="reset">취소</button>
		</form>
	</div>
	<script>
		// 이미지를 감싸고 있는 링크를 클릭했을 때
		document.querySelector("#profileLink").addEventListener("click", ()=>{
			// input type="file"을 강제로 클릭
			document.querySelector("input[name=profileImage]").click();
		});
	
	
		// input 요소중에 name 속성의 값이 profileImage 인 요소를 선택해서 		이벤트 리스너 함수 등록
		document.querySelector("input[name=profileImage]").addEventListener("change", (e)=>{
			// 선택한 파일을 배열로 얻어내기
			const files = e.target.files;
			// FileReader 객체를 생성해서
			const reader=new FileReader;
			// 0번방 파일 객체를 URL로 읽어들이고
			reader.readAsDataURL(files[0]);
			// 다 읽으면 다음 함수 실행
			reader.onload = ()=>{
				// 읽은데이터를 이용해 img 요소 만들 준비 한다
				const img=`<img src="\${reader.result}" 
				style="width:100px;height:100px;border-radius:50%">`;
				// img 마크업 형식의 문자열을 실제로 HTML 로 해석이 되게끔 a 요소 안에 넣기(html로 보여야 브라우저에 띄우니까)
				document.querySelector("#profileLink").innerHTML = img;
			};
		});
	</script>
</body>
</html>