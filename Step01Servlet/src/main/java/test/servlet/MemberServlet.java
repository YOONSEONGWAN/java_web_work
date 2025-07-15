package test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/member")
public class MemberServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get 방식 요청 파라미터 추출 "/member?num=x"
		//String num=request.getParameter("num"); -> 정수값으로 바꾸기 
		// getParameter는 무조건 스트링 리턴
		// 추출과 동시에 문자열로 리턴된 숫자를 실제 정수로 바꾸기 
		int num = Integer.parseInt(request.getParameter("num"));
		// 1번 회원의 정보를 DB 에서 읽어와 응답한다고 가정
		
		// 응답 인코딩 설정
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		// 응답 내용 출력
		PrintWriter pw = response.getWriter();

		pw.println("<!DOCTYPE html>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<meta charset='UTF-8'>");
		pw.println("<title></title>");
		pw.println("<style>");
		pw.println("</style>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<p>"+num+"번 회원의 정보는 다음과 같아요. 이름: xxx, 주소:yyy </p>");
		pw.println("</body>");
		pw.println("</html>");
		pw.close();
	}
}
