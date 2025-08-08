<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/user/signup-form.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
</head>
<body>
	<div class="container">
		<h1>회원가입 양식</h1>
		<%-- 웹브라우저의 자체 폼 검증기능 비활성화  <form  novalidate> --%>
		<form action="signup.jsp" method="post" novalidate> <!-- novalidate 부여해 직접 검증하겠다는 의미 -->
			<div class="mb-2">
				<label class="form-label" for="userName">아이디</label>
				<input class="form-control" type="text" name="userName" id="userName"/>
				<small class="form-text">영문자 소문자로 시작하고 5글자~10글자 이내로 입력하세요</small>
				<div class="valid-feedback">사용 가능한 아이디 입니다.</div>
				<div class="invalid-feedback">사용할 수 없는 아이디 입니다.</div>
			</div>
			<div class="mb-2">
				<label class="form-label" for="pwd">비밀번호</label>
				<input class="form-control" type="password" name="pwd" id="pwd"/>
				<small class="form-text">특수 문자를 하나 이상 조합하세요.</small>
				<div class="invalid-feedback">비밀 번호를 확인 하세요</div>
			</div>
			<div class="mb-2">
				<label class="form-label" for="pwd2">비밀번호 확인</label>
				<input class="form-control" type="password"  id="pwd2"/>
			</div>
			<div class="mb-2">
				<label class="form-label" for="email">이메일</label>
				<input class="form-control" type="email" name="email" id="email"/>
				<div class="invalid-feedback">이메일 형식에 맞게 입력하세요.</div>
			</div>
			<button class="btn btn-primary btn-sm" type="submit" disabled>가입</button>
		</form>
		
	</div>
	<script>
		// 어느 하나라도 invalid 면 가입 버튼 disable로 해주기
		// 아이디 유효성 여부 관리할 변수
		let isIdValid=false;
		// 비밀번호 유효성 여부 관리할 변수
		let isPwdValid=false;
		// 이메일 유효성 여부 관리할 변수
		let isEmailValid=false;
		
		const checkForm = ()=>{
			// 만약 아이디도 유효하고 비밀번호도 유효하고 이메일도 유효하다면
			if(isIdValid && isPwdValid && isEmailValid){
				// 전송버튼 remove disabled (서브밋버튼 선택)
				document.querySelector("[type=submit]").removeAttribute("disabled"); // 속성 추가 제거 함수 
			}else{
				// add disabled
				document.querySelector("[type=submit]").setAttribute("disabled", " ");
			}
		};
		
		document.querySelector("#userName").addEventListener("input", (e)=>{
			// 현재까지 입력한 아이디를 읽어온다.
			const inputId = e.target.value;
			// 영문자 소문자로 시작하고 5글자~10글자 이내로 입력 < 검증조건을 만족했는지 여부를 알아낸다.
			// [첫글자한글자].아무거나 한글자가{4번이상, 9번 이하반복}
			const reg = /^[a-z].{4,9}$/;
			// test 함수에는 t/f 가 리턴 
			if(!reg.test(inputId)){
				e.target.classList.remove("is-valid");
				e.target.classList.add("is-invalid");
				
				// 사용 불가 아이디란 의미로 false 준다
				isIdValid = false;
				return; // 함수를 여기서 종.료.한.다.
			}
			// fetch() 함수를 이용해 get 방식으로 입력한 아이디를 보내고 사용가능 여부를 json 으로 응답받는다.
			fetch("check-id.jsp?inputId="+inputId)
			.then(res=>res.json())
			.then(data=>{
				// 일단 is-valid, is-invalid 클래스를 제거하고
				e.target.classList.remove("is-valid", "is-invalid");
				// data 는 {"canUse" : true} or {"canUse" : false } 구조의 object 이다
				if(data.canUse){ // 사용 가능하다면
					e.target.classList.add("is-valid");
					// 사용할 수 있는 아이디라는 의미로 트루
					isIdValid = true;
				}else{ // 사용 불가라면 (이미 가입된 아이디가 있음)
					e.target.classList.add("is-invalid");
					// 사용 불가 아이디란 의미로 false 준다
					isIdValid = false;
				}
			});
		});
		
		/*
		 - 정규표현식에서 줄인 표현이 있다
		 
		 특수문자가 아닌 한글자 => 	[a-zA-Z0-9_]  => [\w]
		 특수문자 한글자 => 	  	[^a-zA-Z0-9_] => [\W]
		 숫자 한글자 =>         	[0-9]        => [\d]
		 숫자가 아닌 한글자 =>     	[^0-9] 		 => [\D]
		 공백 한글자 => 			[ ] 	     => [\s]
		 공백이 아닌 한글자 => 		[^ ]         => [\S]
		*/
		//함수를 미리 만들어서 
		const checkPwd = ()=>{
			
			//비밀 번호를 검증할 정규 표현식(특수문자 포함여부)
			const reg_pwd=/[\W]/; // a-zA-Z0-9 = \w 임 \W 는 특수문자포함  
			
			//양쪽에 입력한 비밀번호를 읽어와서
			let pwd=document.querySelector("#pwd").value;
			let pwd2=document.querySelector("#pwd2").value;
			
			//일단 is-valid 와 is-invalid 클래스를 제거를 먼저하고 
			document.querySelector("#pwd").classList.remove("is-valid", "is-invalid");
			
			//일단 정규표현식을 만족하는지 확인해서 만족하지 않으면 함수를 여기서 종료
			//만일 첫번째 비밀번호가 정규표현식을 통과하지 못하거나 또는 두번째 비밀번호가 정규표현식을 통과하지 못한다면
			if( !reg_pwd.test(pwd) || !reg_pwd.test(pwd2) ){
				document.querySelector("#pwd").classList.add("is-invalid");
				isPwdValid=false;
				checkForm();
				return;
			}
			
			if(pwd == pwd2){
				document.querySelector("#pwd").classList.add("is-valid");
				//비밀번호가 유효 하다는 의미에서 true 를 넣어준다.
				isPwdValid=true;
			}else{
				document.querySelector("#pwd").classList.add("is-invalid");
				//비밀번호가 유효 하지 않다는 의미에서 false 를 넣어준다.
				isPwdValid=false;
			}
			checkForm();
		};	
		// 비밀번호 혹은 비밀번호 확인란에 입력 했을 때 동일한 함수가 호출되도록 한다. 
		document.querySelector("#pwd").addEventListener("input", checkPwd);
		document.querySelector("#pwd2").addEventListener("input", checkPwd);
		
		document.querySelector("#email").addEventListener("input", (e)=>{
			
			//이메일을 검증할 정규 표현식
			const reg_email=/@/;
			
			//입력한 문자열 읽어오기  (e.target 은 이벤트가 발생한 바로 그 요소의 참조값이다)
			const email=e.target.value;
			
			//일단 is-valid 와 is-invalid 클래스를 제거를 먼저하고 
			document.querySelector("#email").classList.remove("is-valid", "is-invalid");
			
			if(reg_email.test(email)){
				document.querySelector("#email").classList.add("is-valid");
				isEmailValid=true;
			}else{
				document.querySelector("#email").classList.add("is-invalid");
				isEmailValid=false;
			}
			checkForm();
		});		
	</script>
</body>
</html>