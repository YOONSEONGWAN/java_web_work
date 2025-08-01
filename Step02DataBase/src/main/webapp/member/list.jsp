<%@page import="test.util.DbcpBean"%>
<%@page import="test.dto.MemberDto"%>
<%@page import="java.util.List"%>
<%@page import="test.dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	new DbcpBean().getConn();
	MemberDao dao=new MemberDao();
	List<MemberDto> list=dao.selectAll();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/member/list.jsp</title>
<jsp:include page="/WEB-INF/include/resource.jsp"></jsp:include>
</head>
<style>/*
    table {
      width: 80%;
      border-collapse: collapse;
      margin: 20px auto;
    }
    th, td {
      border: 1px solid #ccc;
      padding: 10px;
      text-align: center;
    }
    th {
      background-color: #f5f5f5;
    }
    tr:nth-child(even) {
      background-color: #f9f9f9;
    }*/
  </style> 
<body>
	<jsp:include page="/WEB-INF/include/navbar.jsp">
		<jsp:param value="member" name="thisPage"/>
	</jsp:include>
	<div class="container mt-5">
		<div class="d-flex justify-content-between align-items-center mb-3">
			<h1 class="mb-0" style="text-align:center;">회원 목록</h1>
			<a href="${pageContext.request.contextPath }/member/insertform.jsp" class="btn btn-primary">회원 추가</a>
		</div>	
			<table class="table table-striped table-hover table-bordered text-center">
			  <thead class="table-dark">
			    <tr>
			      <th>번호</th>
			      <th>이름</th>
			      <th>주소</th>
			      <th>수정</th>
			      <th>삭제</th>
			    </tr>
			  </thead>
				  <tbody>
				    <%
				      for(MemberDto tmp : list) {
				    %>
				    <tr>
				      <td><%= tmp.getNum() %></td>
				      <td><%= tmp.getName() %></td>
				      <td><%= tmp.getAddr() %></td>
				      <td><a href="updateform.jsp?num=<%=tmp.getNum()%>" class="btn btn-sm btn-warning">수정</a></td>
				      <td>
					
					   <a href="javascript:" class="delete-link" data-num="<%=tmp.getNum() %>">삭제</a>
				</td>
				    </tr>
				    <%
				      }
				    %>
				  </tbody>
			</table>
		
	</div>
	<jsp:include page="/WEB-INF/include/footer.jsp"></jsp:include>
	<script>
	
		document.querySelectorAll(".delete-link").forEach(item=>{
			//item 은 클릭한 a 요소의 참조값
			item.addEventListener("click", (e)=>{
				const num=e.target.getAttribute("data-num");
				const isDelete=confirm(num+"번 회원을 정말 삭제할까요?ㅠ");
				if(isDelete){
					// delete.jsp 페이지로 이동하게 하면서 삭제할 회원의 번호도 같이 전달.
					location.href="${pageContext.request.contextPath }/member/delete.jsp?num="+num;
			}
			});
		});
	</script>
</body>
</html>