package test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/profile")
public class Profile extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name =request.getParameter("name");
		
		// 응답 인코딩 설정
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		// 응답 내용 출력
		PrintWriter pw = response.getWriter();

		pw.println("<!DOCTYPE html>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<meta charset='UTF-8'>");
		pw.println("<title>Profile Page</title>");
		pw.println("<style>");
		pw.println("</style>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<h1>"+name+"님의 프로필</h1>");
		pw.println("<p>아직은 이름밖에 없어요... 앞으로 자세히 올릴게요</p>");
		pw.println("<a href='/Step01Servlet/friends'>친구 목록으로 돌아가기</a>");
		pw.println("</body>");
		pw.println("</html>");
		pw.close();
		
	}
}
