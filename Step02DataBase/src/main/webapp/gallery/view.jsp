<%@page import="test.dao.CommentDao"%>
<%@page import="test.dto.CommentDto"%>
<%@page import="test.dto.GalleryImageDto"%>
<%@page import="java.util.List"%>
<%@page import="test.dto.GalleryDto"%>
<%@page import="test.dao.GalleryDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	// 자세히 보여줄 gallery 의 PK 얻어내기
	int num = Integer.parseInt(request.getParameter("num"));
	// gallery 정보 얻어오기
	GalleryDto dto = test.dao.GalleryDao.getInstance().getData(num);
	// gallery 에 업로드 된 이미지 목록 얻어오기
	List<GalleryImageDto> images = GalleryDao.getInstance().getImageList(num);
	// 로그인 된 userName (null 일 수 있음)
	String userName =(String)session.getAttribute("userName");
	// 댓글 목록 얻어내기
	List<CommentDto> commentList = CommentDao.getInstance().selectList(num);
	// 로그인 여부 알아내기
	boolean isLogin = userName == null ? false : true;
	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/gallery/view.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"/>
</head>
<body>
	<div class="container">
		<div class="card shadow">
			<!-- 작성자정보 -->
			<div class="card-header d-flex align-items-center">
				<%if(dto.getProfileImage() == null){ %>
					<i style="font-size:100px;" class="bi bi-person-circle"></i>
				<%}else{ %>
					<img src="${pageContext.request.contextPath }/upload/<%=dto.getProfileImage() %>"
					 style="width:100px;height:100px;border-radius:50%;" alt="프로필 이미지" />
				<%} %>
				<strong class="ms-3"><%=dto.getWriter() %></strong>
				<span class="text-muted ms-auto"><%=dto.getCreatedAt()%></span>
			</div>
			<!-- 본문 -->
			<div class="card-body">
				<h5 class="card-title"><%=dto.getTitle() %></h5>
				<%-- textarea 로 입력한 글에서 개행기호는 br 로 변경해서 출력하기 --%>
				<p class="card-text"><%=dto.getContent().replaceAll("\n", "<br>") %></p>
				<div class="row">
					<!-- image 출력 -->
					<% for (GalleryImageDto tmp:images){ %>
						<div class="col-md-6 mb-4">
							<img src="<%=request.getContextPath() %>/upload/<%=tmp.getSaveFileName() %>"
								class="img-fluid rounded" alt="photo" />
						</div>
					<%} %>
				</div><!-- image 출력 row -->
			</div><!-- 본문 -->
		</div><!-- 카드영역 -->
		<a href="${pageContext.request.contextPath }/gallery/list.jsp">목록으로</a>
		<div class="card my-3">
		  <div class="card-header bg-primary text-white">
		    댓글을 입력해 주세요
		  </div>
		  <div class="card-body">
		    <!-- 원글의 댓글을 작성할 폼 -->
		    <form action="save-comment.jsp" method="post">
		      <!-- 숨겨진 입력값 -->
		      <input type="hidden" name="parentNum" value="<%=dto.getNum() %>"/>
		      <input type="hidden" name="targetWriter" value="<%=dto.getWriter() %>" />
		
		      <div class="mb-3">
		        <label for="commentContent" class="form-label">댓글 내용</label>
		        <textarea id="commentContent" name="content" rows="5" class="form-control" placeholder="댓글을 입력하세요"></textarea>
		      </div>
		
		      <button type="submit" class="btn btn-success">등록</button>
		    </form>
		  </div>
		</div>
		<!-- 댓글 목록을 출력하기 -->
		<div class="comments">
		<%for(CommentDto tmp:commentList) { %>
			<!-- 대댓글은 자신의 글번호와 댓글의 그룹번호가 다르다. 그런 경우에 왼쪽 마진을 부여한다. -->
			<div class="card mt-3 mb-3 <%=tmp.getNum()==tmp.getGroupNum() ?"":"ms-5" %>">
				<%if(tmp.getDeleted().equals("YES")){ %>
					<div class="card-body bg-light text-muted rounded">삭제된 댓글입니다.</div>
				<%}else{ %>
		        	<div class="card-body d-flex flex-column flex-sm-row position-relative">
		        		<%if(tmp.getNum() != tmp.getGroupNum()){ %>
		            		<i class="bi bi-arrow-return-right position-absolute" style="top:0;left:-30px"></i>
		            	<%} %>
			            <%-- 댓글 작성자가 로그인 된 userName과 같을 때만 삭제 버튼 출력  --%>
			            <%if(tmp.getWriter().equals(userName)){%>
			            	<button data-num="<%=tmp.getNum() %>" class="btn-close position-absolute top-0 end-0 m-1"></button>
			            <%}else{ %>
			            
			            <%} %>
		            	<%if(tmp.getProfileImage() == null){ %>
		            		<i style="font-size:50px" class="bi bi-person-circle me-3 align-self-center"></i>
		            	<%}else{ %>
		            		<img class="rounded-circle me-3 align-self-center" 
			                	src="${pageContext.request.contextPath }/upload/<%=tmp.getProfileImage() %>" 
			                	alt="프로필 이미지" 
			                	style="width:50px; height:50px;">
		            	<%} %>
		                <div class="flex-grow-1">
		                    <div class="d-flex justify-content-between">
		                        <div>
		                            <strong><%=tmp.getWriter() %></strong>
		                            <span>@<%=tmp.getTargetWriter() %></span>
		                        </div>
		                        <small><%=tmp.getCreatedAt() %></small>
		                    </div>
		                    <pre><%=tmp.getContent() %></pre>
		                    <%-- 댓글 작성자가 로그인된 userName 과 같으면 수정폼, 다르면 댓글폼을 출력한다. --%>
		                    <%if(tmp.getWriter().equals(userName)){ %>
	                    	    <button class="btn btn-sm btn-outline-secondary edit-btn">수정</button>
			                    <!-- 댓글 입력 폼(처음에는 숨김)-->
			                    <div class="d-none form-div">
			                        <form action="comment-update.jsp" method="post">
			                        	<!-- 댓글을 수정하기 위한 댓글의 번호, 이 페이지로 다시 돌아오기 위한 parentNum 도 같이 전송되도록 -->
			                        	<input type="hidden" name="num" value="<%=tmp.getNum() %>" />
			                        	<input type="hidden" name="parentNum" value="<%=num %>" />
			                            <textarea name="content" class="form-control mb-2" rows="2" ><%=tmp.getContent() %></textarea>
			                            <button type="submit" class="btn btn-sm btn-success">수정 완료</button>
			                            <button type="reset" class="btn btn-sm btn-secondary cancel-edit-btn">취소</button>
			                        </form>
			                    </div>
		                    <%}else{ %>
			                    <button class="btn btn-sm btn-outline-primary show-reply-btn">댓글</button>
			                    <!-- 댓글 입력 폼(처음에는 숨김)-->
			                    <div class="d-none form-div">
			                        <form action="save-comment.jsp" method="post">
			                        	<!-- 원글의 글번호, 댓글 대상자의 userName, 댓글의 그룹번호도 같이 전송해야한다. -->
			                        	<input type="hidden" name="parentNum" value="<%=dto.getNum() %>" />
			                        	<input type="hidden" name="targetWriter" value="<%=tmp.getWriter() %>" /><!-- 댓글의 작성자 -->
			                        	<input type="hidden" name="groupNum" value="<%=tmp.getGroupNum() %>" /><!-- 댓글의 글번호가 그룹번호가됨 -->
			                            <textarea name="content" class="form-control mb-2" rows="2" placeholder="댓글을 입력하세요..."></textarea>
			                            <button type="submit" class="btn btn-sm btn-success">등록</button>
			                            <button type="reset" class="btn btn-sm btn-secondary cancel-reply-btn">취소</button>
			                        </form>
		                    <%} %>
		                    </div>
		                </div>
		            </div> <!-- card-body -->
				<%} %>
	        </div> <!-- card -->
			<%} %>
		</div><!-- comments -->
	</div><!-- container -->
	<script>
	const isLogin= <%=isLogin %> ;
	
	// class 명이 edit-btn인 모든 버튼에 click 이벤트 리스너 등록
    document.querySelectorAll(".edit-btn").forEach( item => {
        item.addEventListener("click", ()=>{
            // 클릭한 버튼의 다음 형제요소의 class 목록에서 d-none 제거
            item.nextElementSibling.classList.remove("d-none");
            // 클릭한 버튼의 class 목록에 d-none 추가
            item.classList.add("d-none");
        });
    });
    // 취소 버튼을 눌렀을 때 이벤트 리스너 등록 
    document.querySelectorAll(".cancel-edit-btn").forEach(item=>{
        item.addEventListener("click", ()=>{
            // 가장 가까운 부모요소 중 .form-div 요소
            const formDiv=item.closest(".form-div");
            // formDiv 에 d-none 클래스 추가해서 안 보이게 하고
            formDiv.classList.add("d-none");
            // formDiv 의 이전 형제요소(댓글버튼)의 d-none 제거
            formDiv.previousElementSibling.classList.remove("d-none");
        });
    });
    // 삭제 버튼을 눌렀을 때
    document.querySelectorAll(".btn-close").forEach(item=>{
        item.addEventListener("click", ()=>{
            // data-num 속성에 출력된 삭제할 댓글의 번호 attribute에는 속성값이 다 있다.
            const num=item.getAttribute("data-num");
            const isDelete=confirm(num+"번 댓글을 삭제 하시겠습니까?")
            if(isDelete){ //true 일때 삭제, false일때 취소
            	// n번 글에 달린n번 댓글을 삭제하겟다. \$ 앞에 역슬래시로 브라우저가 읽지 않도록 
            	// delte.jsp?num=삭제할 댓글 번호&parentNum=원글의 글번호 형식의 요청이 되도록 한다.
            	location.href=`comment-delete.jsp?num=\${num}&parentNum=<%=num %>`;
            }
        });
    })
	
	document.querySelector("#commentContent").addEventListener("input", ()=>{
		// 원글의 댓글 입력란에 포커스가 왔을 때 만일 로그인 하지 않았다면
		if(!isLogin){
			alert("댓글 작성을 위해 로그인이 필요합니다!");
			location.href= 
				"${pageContext.request.contextPath }/user/loginform.jsp?url=${pageContext.request.contextPath }/board/view.jsp?num=<%=num %>";
		}
	});
	
    // 모든 댓글 버튼에 이벤트 등록 [버튼참조1, 버튼참조2, ...]
    document.querySelectorAll(".show-reply-btn").forEach(item=>{
        // 매개변수에 전달된 item 은 댓글 button 의 참조값이다.
        item.addEventListener("click", ()=>{ 
        	// 댓글 버튼을 눌렀을 때 만일 로그인 하지 않았다면
			if(!isLogin){
				alert("댓글 작성을 위해 로그인이 필요합니다!");
    			location.href= 
    				"${pageContext.request.contextPath }/user/loginform.jsp?url=${pageContext.request.contextPath }/board/view.jsp?num=<%=num %>";
    			return; // 리턴으로 아래 폼 안 열리게 하기
			}
            // 클릭한 버튼의 다음 형제요소의 class 목록에서 d-none 제거
            item.nextElementSibling.classList.remove("d-none");
            // 클릭한 버튼의 class 목록에 d-none 을 추가
            item.classList.add("d-none")
        });
    });
    // 취소를 누르면 댓글폼이 닫히도록 하기
    document.querySelectorAll(".cancel-reply-btn").forEach(item=>{
        item.addEventListener("click", ()=>{
            // 가장 가까운 부모요소 중 .form-div 요소
            const formDiv=item.closest(".form-div");
            // formDiv 에 d-none 클래스 추가해서 안 보이게 하고
            formDiv.classList.add("d-none");
            // formDiv 의 이전 형제요소(댓글버튼)의 d-none 제거
            formDiv.previousElementSibling.classList.remove("d-none");
        });
    });
	</script>
</body>
</html>