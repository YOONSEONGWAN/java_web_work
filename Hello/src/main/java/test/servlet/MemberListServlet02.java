package test.servlet;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import test.dao.MemberDao;
import test.dto.MemberDto;

@WebServlet("/members") // 이 요청에 대해서 응답한다는 걸 알려줌
public class MemberListServlet02 extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// members list 
        List<MemberDto> memberlist=new MemberDao().selectAll();
        
		// 응답 인코딩 설정
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
       
        
        // 응답 내용 출력
        PrintWriter pw = response.getWriter();
        
        pw.println("<!DOCTYPE html>");
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<meta charset='UTF-8'>");
        pw.println("<title>회원 목록</title>");
        pw.println("<style>");
        pw.println("table { width: 80%; border-collapse: collapse; margin: 20px auto; }");
        pw.println("th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }");
        pw.println("th { background-color: #f2f2f2; }");
        pw.println("</style>");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<h2 style='text-align:center;'>📋 전체 회원 목록</h2>");

        pw.println("<table>");
        pw.println("<tr><th>번호</th><th>이름</th><th>주소</th></tr>");
        
        
        for(MemberDto tmp : memberlist) {
        	 pw.println("<tr>");
	             pw.println("<td>" + tmp.getNum() + "</td>");
	             pw.println("<td>" + tmp.getName() + "</td>");
	             pw.println("<td>" + tmp.getAddr() + "</td>");
             pw.println("</tr>");
			}; 
			pw.println("</table>");
	        pw.println("</body>");
	        pw.println("</html>");
	        pw.close();
	}
}
