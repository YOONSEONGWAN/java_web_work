package test.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/test/fileup")
@MultipartConfig(
		fileSizeThreshold = 1024*1024*10, // 업로드 처리하기 위한 메모리 사이즈(10MB)
		maxFileSize = 1024*1024*50, // 업로드 되는 최대 파일 사이즈 (50MB)
		maxRequestSize = 1024*1024*60 // 이 요청의 최대 사이즈 (60MB), 파일 외에 다른 문자열도 전송되기 때문에 
	)//파일이 업로드 되기 때문에 멀티파트 어노테이션이 필요하다.
public class FileUpServlet extends HttpServlet {
	// 필드 선언 
	String fileLocation; // c:/playground/upload 이걸 여기에 하드코딩하면 번거로워진다.
	
	// 이 서블릿이 초기화 되는 시점에 최초 1번 호출되는 메소드 
		@Override
		public void init() throws ServletException {
			// 무언가 초기화 작업을 여기서 하면 된다. 
			ServletContext context = getServletContext();
			// web.xml 파일에 "fileLocation" 이란 이름으로 저장된 정보 읽어와서 필드에 저장
			fileLocation = context.getInitParameter("fileLocation");
			// c:/playground/upload 이 경로에 저장될 예정(web.xml에서 지정해둔 경로)
		}
		// post 방식 요청만 처리 할 거라면 doPost() method 오버라이드( 두겟과 둘다는 다른 오버라이드)
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			/*
			 * 	파일폼.jsp에 입력한 input 요소의 속성값 파라미터로 받아오기 
			 *  "caption" 이라는 파라미터 명으로 문자열이 전송되고
			 *  "myFile" 이라는 명으로 파일이 전송된다. (추출 방식이 다름)
			 */
			String caption=req.getParameter("caption");
			// <input type="file" name="myFile"
			Part filePart = req.getPart("myFile");
			
			// if 문 아래는 임시폴더로부터 복사해서 가져오는 과정임 
			// 만일 업로드 된 프로필 이미지가 있다면 (수정하지 않았으면 없음, 기본프사) 
			if(filePart!=null && filePart.getSize()>0) {
				// 원본 파일의 이름 얻어내기 1번 
				String orgFileName=filePart.getSubmittedFileName(); 
				// 파일명이 겹치지 않게 랜덤한 id 값 얻기. 2번
				String uid=UUID.randomUUID().toString();
				// 저장될 파일명 구성한다. 1+2번 = 3번
				String saveFileName=uid+orgFileName;
				// 저장할 파일의 경로 구성하기. 이걸 아래 filePath 매개변수로 보냄.  xml 설정한 파일경로+3번 = 4번
				String filePath=fileLocation+"/"+saveFileName;
				
				/*
				 * 	업로드 된 파일은 임시 폴더에 임시 파일로 저장이 된다.
				 * 	임시폴더에 들어간 원래파일과 uid 를 얻은 저장된 파일명이 다르다 -> 이 정보를 모두 DB에 저장을 해두어야 다운로드가 된다.
				 * 	해당 파일에서 byte 알갱이를 읽어들일 수 있는 InputStream 객체를 얻어내어
				 */
				InputStream is=filePart.getInputStream(); // 4번.method = 5번
				// 원하는 목적지에 copy 해야 한다.
				Files.copy(is, Paths.get(filePath)); // 4, 메소드에(5번)
				// 업로드 된 파일의 크기
				long fileSize = filePart.getSize();
				
				/*
				 *  원래는 DB 에 저장해야 하지만 테스트로 응답이 필요한 데이터를
				 *  HttpServletRequest 객체의 .setAttribute(키, 값) 메소드를 이용해 저장하고
				 *  jsp 페이지가 사용할 수 있도록 한다.
				 *  jsp 페이지에서 해당 정보를 얻어낼 떄에는
				 *  HttpSertvletRequest 객체의 .getAttribute(키) 메소드를 이용해 얻어낸다
				 *  .getAttribute는 Object type 으로 리턴되기 때문에 타입을 기억하다가 원래 type 으로 casting 필요
				 */
				req.setAttribute("orgFileName", orgFileName); // String type
				req.setAttribute("saveFileName", saveFileName); // String type
				req.setAttribute("fileSize", fileSize); // long type
				
				
			} // end if
			
			// 요청 전달자 객체 얻어내기
			RequestDispatcher rd=req.getRequestDispatcher("/test/윤제.jsp");
			// 응답을 위임하기
			rd.forward(req, resp); // req resp 객체를 전달해준다. 
			
		} // end method
} // end class
