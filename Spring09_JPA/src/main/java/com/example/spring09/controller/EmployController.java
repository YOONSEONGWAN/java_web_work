package com.example.spring09.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.spring09.dto.DeptDto;
import com.example.spring09.dto.EmpDeptDto;
import com.example.spring09.dto.EmpDto;
import com.example.spring09.service.BookServiceImpl;
import com.example.spring09.service.EmployService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class EmployController {

    private final BookServiceImpl bookServiceImpl;

	private final EmployService employService;

	
	@GetMapping("/emps")
	public String empList(Model model) {
		// 사원 목록을 Model 객체에 담는다.
		model.addAttribute("empList", employService.getEmpList());
		return "emps/list";
	}
	
	@GetMapping("/emps/{empno}")
	public String empDetail(@PathVariable int empno, Model model) {
		// 사원의 자세한 정보
		EmpDeptDto dto = employService.getEmpDetail(empno);
		// 응답에 필요한 데이터를 "emp" 라는 키값으로 담기 
		model.addAttribute("emp", dto);
		return "emps/detail";
	}
	
	@GetMapping("/depts")
	public String deptList(Model model) {
		// 부서 목록
		List<DeptDto> deptList = employService.getDeptList();
		model.addAttribute("deptList", deptList);
		return "depts/list";
	}
	
	@GetMapping("/depts/{deptno}")
	public String deptDetail(@PathVariable int deptno, Model model) {
		// 경로변수에 전달된 deptno 를 이용해 부서의 자세한 정보 얻어와서
		DeptDto dto = employService.getDeptDetail(deptno);
		// 응답에 필요한 데이터를 "emp" 라는 키값으로 model 에 담기 
		model.addAttribute("dept", dto);
		
		// 해당 부서에 근무하는 사원 정보도 얻어와 model 에 담는다.
		List<EmpDto> empList=employService.getEmpListByDeptno(deptno);
		model.addAttribute("empList",empList);
		
		return "depts/detail";
	}
}
