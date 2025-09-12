package com.example.spring09.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring09.dto.ClientDto;
import com.example.spring09.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/v1")
@RestController
@RequiredArgsConstructor
@Tag(name = "Clients", description = "고객 관리 API") // swagger UI 에 제공할 정보
public class RestClientController {
	
	private final ClientService clientService;

	
	// clients list
	@Operation(summary = "고객 목록 조회", description = "전체 고객을 페이징 없이 반환합니다.")
	@GetMapping("/clients")
	public List<ClientDto> list(){
		List<ClientDto> list = clientService.getClients();
		return list;
	}
	
	// create one of clients
	@PostMapping("/clients")
	public ClientDto create(@Valid @RequestBody ClientDto dto) {
		Long id=clientService.addClient(dto);
		dto.setNum(id);
		
		return dto;
				
	}
	
	// 정보 수정
	@PutMapping("/clients/{num}")
	public ClientDto update(@PathVariable Long num, @Valid @RequestBody ClientDto dto) {
		dto.setNum(num);
		clientService.updateClient(dto);
		return dto;
	}
	
	// 정보 자세히 보기
	@GetMapping("/clients/{num}")
	public ClientDto detail(@PathVariable Long num) {
		return clientService.getClient(num);
	}

}
