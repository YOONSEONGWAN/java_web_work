package test.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import test.dao.UserDao;
import test.dto.UserDto;
/*
 * 클라이언트의 요청(HTTP Request)을 받아서
	그에 맞는 응답(HTTP Response)을 만드는 자바 클래스
 * 	enctype="multipart/form-data" 형식의 폼이 전송되었을 때 처리할 서블릿 만들기
 * 	
 */
@WebServlet("/user/update")
@MultipartConfig(
	fileSizeThreshold = 1024*1024*10, // 업로드 처리하기 위한 메모리 사이즈(10MB)
	maxFileSize = 1024*1024*50, // 업로드 되는 최대 파일 사이즈 (50MB)
	maxRequestSize = 1024*1024*60 // 이 요청의 최대 사이즈 (60MB), 파일 외에 다른 문자열도 전송되기 때문에 
)
public class UserUpdateServlet extends HttpServlet {
	
	// 업로드 된 파일 저장경로를 저장할 필드 선언
	String fileLocation;
	
	// 이 서블릿이 초기화 되는 시점에 최초 1번 호출되는 메소드 
	@Override
	public void init() throws ServletException {
		// 무언가 초기화 작업을 여기서 하면 된다. 
		ServletContext context = getServletContext();
		// web.xml 파일에 "fileLocation" 이란 이름으로 저장된 정보 읽어와서 필드에 저장
		fileLocation = context.getInitParameter("fileLocation");
	}
	
	
	// post 방식 전송되었을 때 호출되는 메소드
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 폼 전송되는 내용 추출 
		String userName=req.getParameter("userName");
		String email=req.getParameter("email");
		// file data (<input type="file" name="profileImage"/>)
		Part filePart=req.getPart("profileImage");
		// DB에서 사용자 정보를 불러온다.
		UserDto dto=UserDao.getInstance().getByUserName(userName); 
		
		// 만일 업로드 된 프로필 이미지가 있다면 (수정하지 않았으면 없음, 기본프사) 
		if(filePart!=null && filePart.getSize()>0) {
			// 원본 파일의 이름 얻어내기
			String orgFileName=filePart.getSubmittedFileName();
			// 파일명이 겹치지 않게 랜덤한 id 값 얻기
			String uid=UUID.randomUUID().toString();
			// 저장될 파일명 구성한다.
			String saveFileName=uid+orgFileName;
			// 저장할 파일의 경로 구성하기. 이걸 아래 filePath 매개변수로 보냄
			String filePath=fileLocation+"/"+saveFileName;
			
			/*
			 * 	업로드 된 파일은 임시 폴더에 임시 파일로 저장이 된다.
			 * 	해당 파일에서 byte 알갱이를 읽어들일 수 있는 InputStream 객체를 얻어내어
			 */
			InputStream is=filePart.getInputStream();
			// 원하는 목적지에 copy 해야 한다.
			Files.copy(is, Paths.get(filePath));
			// 기존에 이미 저장된 프로필 사진이 있으면 파일 시스템에서 삭제하기
			if(dto.getProfileImage() !=null) {
				// 삭제할 파일의 경로(전체)
				String deleteFilePath=fileLocation+"/"+dto.getProfileImage();
				// Files 클래스의 delete() method 이용해서 삭제하기. 
				Files.delete(Paths.get(deleteFilePath));
			}
			
			// dto 에 이메일과 저장된 파일명을 담는다.
			dto.setEmail(email);
			dto.setProfileImage(saveFileName);
			// dao 의 email 과 profile 을 수정하는 메소드를 이용해서 수정 반영
			UserDao.getInstance().updateEmailProfile(dto);
			
		}else { // 업로드 된 프로필 이미지가 없다면 (이메일만 수정하는 경우)
			// dto 에 이메일만 담는다.
			dto.setEmail(email);
			// dao 의 eamil 만 수정하는 메소드를 이용해서 수정 반영
			UserDao.getInstance().updateEmail(dto);
		}
		
		// 리다일렉트 응답 doPost 메소드 안에 작성
		String cPath=req.getContextPath();
		resp.sendRedirect(cPath+"/user/info.jsp");
		
	}
}
