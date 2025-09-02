package com.example.spring09.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.spring09.dto.ClientDto;
import com.example.spring09.service.ClientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/*
 * 	- 1. client 목록 요청
 * 
 * 	기존: GET "/client/list"
 * 	변경: GET "/clients"
 * 
 *  - 2. client 상세보기 요청
 *  
 *  기존: GET "/client/detail?num=x"
 *  변경: GET "/clients/x"
 *  
 *  - 3. client 추가 form 요청
 *  
 *  기존: GET "/client/new-form"
 *  변경: GET "/clients/new"
 *  
 * 	- 4. client 실제 추가 요청
 * 	
 * 	기존: POST "/client/save"
 * 	변경: POST "/clients"
 * 
 * 	- 5. client 수정 form 요청 
 * 
 * 	기존: GET "/client/edit?num=x"
 * 	변경: GET "/clients/x/edit"
 * 
 * 	- 6. client 수정 반영 요청
 * 
 * 	기존: POST "/client/update"
 * 	변경: POST "/clients/x"
 * 
 *  
 */

@RequiredArgsConstructor
@Controller
public class ClientController {
	// 의존객체
	private final ClientService clientService;
	
	@GetMapping("/clients")
	public String list(Model model) {
		// 응답에 필요한 데이터를 model 객체에 담는다.
		model.addAttribute("clients", clientService.getClients());
		// view page 에서 응답.
		return "clients/list";
	}
	
	// 상세 보기 
	@GetMapping("/clients/{num}")
	public String viewClients(@PathVariable("num") Long num, Model model) {
		ClientDto dto=clientService.getCliendt(num);
	
		model.addAttribute("client", dto);
		
		return "clients/detail";
	}
	
	// new form
	@GetMapping("/clients/new") 
	public String newForm(Model model){
		// 만약 "clientDto" 라는 키값으로 저장되어 있는 값이 model 객체에 없다면
		if(!model.containsAttribute("clientDto")) {
			// 빈 clientDto 객체라도 전달을 해주어야 한다. 전달해주지 않으면 thymeleaf 페이지에서 에러 발생 
				model.addAttribute("clientDto", new ClientDto());
		}
		
		return "clients/new";
	}
	
	// create
	/*
	 * 	@valid 어노테이션을 이용해서 dto 의 필드를 자동 검증하기 위한 의존 depencency 를 pom.xml 에 추가
	 * 
	 * 	<dependency>
	 *	  <groupId>org.springframework.boot</groupId>
	 *	  <artifactId>spring-boot-starter-validation</artifactId>
	 * 	</dependency>
	 * 	
	 * 	@Valid 가 dto 의 어떤 필드를 어떤 조건으로 검증할지 Dto 클래스에 표시해야함
	 * 
	 * 	검증 결과가 BindingResult 객체에 담겨서 전달이 된다.
	 * 
	 * 	@Valid 로 검증을 한 dto 매개변수 선언 바로 뒤에 BindingResult 매개변수를 선언해야 한다.
	 */
	@PostMapping("/clients")
	public String create(@Valid ClientDto dto, BindingResult br, RedirectAttributes ra) {
		// 폼 입력 내용중에 에러가 있는지 (검증조건을 통과하지 못했는지) 여부를 알아내서
		boolean hasError=br.hasErrors();
		// 만약 에러가 있다면 다시 폼으로 리다일렉팅 
		if(hasError) {
			/*
			 * 	검증 결과를 thymeleaf view page 에서 활용하려면 
			 * 	Model 객체 혹은 RedirectAttributes(리다일렉트 이동할 경우만) 객체에 정보를 담아주어야 한다.
			 * 	
			 * 	model.addAttribute("key", dto 객체);
			 * 	model.addAttribute("org.springframework.validation.BindingResult.key값", BindingResult 객체);
			 * 
			 * 	or
			 * 
			 * 	ra.model.addFlashAttribute("key 값", dto 객체);
			 * 	ra.model.addAttribute("org.springframework.validation.BindingResult.key값", BindingResult 객체);
			 * 
			 * 	두 개가 반드시 세트로 key 값을 일치시켜서 담아야함
			 */
			ra.addFlashAttribute("clientDto", dto);
			ra.addFlashAttribute("org.springframework.validation.BindingResult.clientDto", br);
			
			return "redirect:/clients/new";
		}
		// 새 고객 정보를 저장한다.
		Long num = clientService.addClient(dto);
		
		ra.addFlashAttribute("msg", "저장 완료!");
		// 고객 정보 자세히 보기로 리다일렉트
		return "redirect:/clients/" + num;
	}
	
	// birthday update form
	@GetMapping("/clients/{num}/edit")
	public String updateForm(@PathVariable("num") Long num, Model model) {
		ClientDto dto=clientService.getCliendt(num);
		model.addAttribute("dto", dto);
		
		return "clients/edit";
	}
	
	// birthday update
	@PostMapping("/clients/{num}")
	public String update(Long num, LocalDate birthday) {
		clientService.updateBirthday(num, birthday);
		
		return "redirect:/clients";
	}
	
}
