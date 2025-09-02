package com.example.spring09.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring09.dto.ClientDto;
import com.example.spring09.entity.Client;
import com.example.spring09.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // lombok 이 생성자를 자동으로 만들어준다.
public class ClientServiceImpl implements ClientService {
	// 의존객체 생성자 주입
	private final ClientRepository clientRepo;
	
	
	// Client 정보 저장
	@Transactional
	@Override
	public Long addClient(ClientDto dto) {
		// dto 를 entity 로 변경해서 저장, 리턴값은 저장한 Client entity 객체 하나가 리턴된다.
		Client saved=clientRepo.save(dto.toEntity());
		// entity 에 있는 번호 리턴 
		return saved.getNum();
	}

	// Client 목록 조회
	@Transactional(readOnly = true)
	@Override
	public List<ClientDto> getClients() {
		// entity List 를 stream 으로 만들어서 map() 함수를 이용. dto의 stream 으로 만든 다음
		// dto list 로 변경하기
		List<ClientDto> dtoList = clientRepo.findAll().stream().map(ClientDto::toDto).toList();
		
		return dtoList;
	}
	
	// Client 한 명의 정보 조회
	@Transactional(readOnly = true)
	@Override
	public ClientDto getCliendt(Long num) {
		// Client entity=clientRepo.findById(num).get(); // 예외 없이 사용
		Client entity=clientRepo.findById(num)
				.orElseThrow(()-> new IllegalArgumentException("회원이 존재하지 않아요 ; ; num="+num));
		return ClientDto.toDto(entity);
	}

	@Transactional
	@Override
	public void updateBirthday(Long num, LocalDate birthday) {
		Client entity = clientRepo.findById(num)
				.orElseThrow(()-> new IllegalArgumentException("수정할 회원이 존재하지 않아요 ; ; "));
		// 생일 날짜를 넣어준다.
		entity.setBirthday(birthday); // entity 를 수정하는 것 만으로 자동으로 반영된다. 
		
	}
	
	

}
