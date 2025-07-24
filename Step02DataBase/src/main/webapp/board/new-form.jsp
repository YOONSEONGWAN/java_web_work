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
		<form action="save.jsp" method="post" id="saveForm">
			<div class="mb-2">
				<label class="form-label" for="title">제목</label>
				<input class="form-control" type="text" name="title" id="title" />
			</div>
			<div class="mb-2">
				<label class="form-lable" for="editor">내용</label>
				<!-- Editor UI 가 출력될 div -->
				<div id="editor"></div>
				
				<textarea class="form-control" name="content" id="hiddencontent" ></textarea>
			</div>
			<button class="btn btn-success btn-sm" type="submit">저장</button>
			<a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath }/board/list.jsp">취소</a>
		</form>
	</div>
	<script>
		// 위에 toast ui javascript 가 로딩 되어 있으면, toastui.Editor 클래스를 생성할 수 있다.
		// 해당 클래스를 이용해서 객체 생성하면서 {} object 로 ui 에 관련된 옵션을 잘 전달하면 
		// 우리가 원하는 모양의 텍스트 편집기를 만들 수 있다. 
		const editor = new toastui.Editor({
			el: document.querySelector('#editor'),
			height: '500px',
			initialEditType: 'wysiwyg',
			previewStyle: 'vertical',
			language: 'ko'
		});
		
		document.querySelector("#saveForm").addEventListener("submit", (e)=>{
			// Editor 로 작성된 문자열 읽어오기
			const content = editor.getHTML();
			// 테스트로 콘솔에 출력하기
			console.log(content);
			// editor 로 작성된 문자열을 폼 전송이 될 수 있는 textarea 의 value 로 넣어준다.
			document.querySelector("#hiddencontent").value=content;
			// 테스트 하기 위해 폼 전송 막기
			//e.preventDefault();
		})
	</script>
</body>
</html>