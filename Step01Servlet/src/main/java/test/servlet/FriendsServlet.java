package test.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/friends")
public class FriendsServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// db 에서 읽어온 친구목록이라고 가정
		List<String> names=new ArrayList<>();
		names.add("김구라");
		names.add("해골");
		names.add("원숭이");
		
		// 응답 인코딩 설정
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		// 응답 내용 출력
		PrintWriter pw = response.getWriter();

		pw.println("<!DOCTYPE html>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<meta charset='UTF-8'>");
		pw.println("<title>친구 목록</title>");
		pw.println("<style>");
		pw.println("body { font-family: '맑은 고딕'; background: #f0f0f0; }");
		pw.println("table { border-collapse: collapse; width: 500px; margin: 20px auto; background: white; }");
		pw.println("th, td { padding: 10px; text-align: center; border: 1px solid gray; }");
		pw.println("h1 { text-align: center; color: navy; }");
		pw.println("</style>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<h1>친구 목록입니다</h1>");
		pw.println("<ul>");
		pw.println("<table border='1'>");
		pw.println("<tr><th>번호</th><th>이름</th><th>상세</th></tr>");
		int i = 1;
			for(String tmp : names) {
				pw.println("<tr>");
				pw.println("<td>"+i+"</td>");
				pw.println("<td>"+tmp+"</td>");
				pw.println("<td>"+"<button onclick='alert(\"프로필 보는 기능을 준비중입니다\")'>프로필 보기</button> "+"</td>");
				pw.println("<td><a href='/profile?name="+tmp+"'>자세히</a>");
				pw.println("</tr>");
				i++;
			}
		pw.println("</ul>");
		pw.println("</table>");
		pw.println("<button><a href=\"/Step01Servlet\">index page로 돌아가기</a></button>");
		pw.println("<p>총 친구 수:"+names.size()+"명</p>");
		pw.println("</body>");
		pw.println("</html>");
		pw.close();
		
	}
}
