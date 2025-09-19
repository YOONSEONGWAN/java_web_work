package com.example.spring10.service;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.spring10.dto.PwdChangeRequest;
import com.example.spring10.dto.UserDto;
import com.example.spring10.exception.PasswordException;
import com.example.spring10.repository.UserDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	private final UserDao dao;
	// 비밀번호를 암호화하기 위한 객체도 spring bean container 로부터 주입받는다.
	private final PasswordEncoder encoder;
	// 업로드 된 이미지를 저장할 위치 얻어내기
	@Value("${file.location}")
	private String fileLocation;
	
	// 사용자를 추가하는 메소드 
	@Override
	public void createUser(UserDto dto) {
		// 날것의 비밀번호를 암호화해서 (1234->fdh$!@#!)
		String encodedPwd = encoder.encode(dto.getPassword());
		// dto 에 다시 담는다
		dto.setPassword(encodedPwd);
		// DB 에 저장하기. 
		dao.insert(dto);
	}

	@Override
	public UserDto getUser(String name) {
		return dao.getByUserName(name) ;
	}

	@Override
	public void updatePassword(PwdChangeRequest pcr) {
		// 로그인 된 userName
//		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		// DB 에 저장된 암호화 비밀번호 읽어오기
		UserDto dto=dao.getByUserName(pcr.getUserName());
		String encodedPwd = dto.getPassword();
		// 암호화 된 비밀번호 입력한 비밀번호를 비교해서 일치하는지 확인하기
		boolean isValid = BCrypt.checkpw(pcr.getPassword(), encodedPwd);
		// 만일 일치하지 않으면 예외 발생 시키기
		if(!isValid) {
//			throw new RuntimeException("기존 비밀번호가 일치하지 않습니다."); 500번 에러 응답
			
			// 400 번 에러 응답 -> 간편하게 예외 던지기
			//throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "기존 비밀번호가 일치하지 않아요 ! ");
			
			throw new PasswordException("기존 비밀번호가 일치하지 않아요!");
		}
		// 일치하면 새 비밀번호를 암호화해서 pcr 객체에 담은 다음 DB 에 수정 반영한다.
		dto.setPassword(encoder.encode(pcr.getNewPassword())); // 
		dao.updatePassword(dto);
	}

	@Override
	public Map<String, Object> canUseId(String id) {
		// id 를 이용해서 DB 에서 해당 아이디로 가입된 정보가 있는지 읽어와본다. (없으면 null 리턴)
		UserDto dto = dao.getByUserName(id);
		// id 가 사용가능한지 여부(dto 가 null 이면 true 값 준다)
		boolean canUse = dto == null ? true : false ; 
		// Map 에 담아 리턴 -> true 나 false 가 json 으로 응답됨
		return Map.of("canUse", canUse);
	}

	@Override
	public void updateUser(UserDto dto) {
		// 업로드된 이미지 있는지 읽어와보기
		MultipartFile image=dto.getProfileFile();
		// 만일 업로드된 이미지가 있다면
		if(!image.isEmpty()) {
			// 원본 파일명
			String orgFileName = image.getOriginalFilename();
			// 이미지의 확장자를 유지하기 위해 뒤에 원본 파일명을 추가
			String saveFileName = UUID.randomUUID().toString()+orgFileName;
			// 저장할 파일의 전체 경로 구성
			String filePath = fileLocation + File.separator+saveFileName;
			try {
				// 업로드된 파일을 저장할 파일 객체 생성
				File saveFile = new File(filePath);
				image.transferTo(saveFile);
			}catch(Exception e) {
				e.printStackTrace();
			}
			// UserDto 에 저장된 이미지의 이름을 넣어준다.
			dto.setProfileImage(saveFileName);
		}
		// UserDao 객체 이용해서 수정 반영하기 (dto 의 profileImage 는 null 일수도 있다)
		dao.update(dto);
	}

}
