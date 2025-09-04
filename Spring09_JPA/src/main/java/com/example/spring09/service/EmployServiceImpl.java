package com.example.spring09.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring09.dto.DeptDto;
import com.example.spring09.dto.EmpDeptDto;
import com.example.spring09.dto.EmpDto;
import com.example.spring09.entity.Emp;
import com.example.spring09.repository.DeptRepository;
import com.example.spring09.repository.EmpRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployServiceImpl implements EmployService {

	private final EmpRepository empRepo;
	private final DeptRepository deptRepo;
	
	@Transactional(readOnly = true)
	@Override
	public List<EmpDto> getEmpList() {
		// Entity List 를 Dto List 로 바꾸는 과정 (stream 으로 바꾸어야 map() 사용가능)
		List<EmpDto> empDtoList = empRepo.findAll().stream().map(EmpDto::toDto).toList();
		
		return empDtoList;
	}

	@Transactional(readOnly = true)
	@Override
	public List<DeptDto> getDeptList() {
		
		List<DeptDto> deptDtoList = deptRepo.findAll().stream().map(DeptDto::toDto).toList();
		
		return deptDtoList;
	}

	@Transactional(readOnly = true)
	@Override
	public EmpDeptDto getEmpDetail(int empno) {
		// 사원번호를 이용해 Emp entity 를 얻어내고 
		Emp entity = empRepo.findById(empno).get();
		// Emp entity 를 EmpDeptDto 로 변경해서 리턴한다.
		return EmpDeptDto.toDto(entity);
	}

	@Transactional(readOnly = true)
	@Override
	public DeptDto getDeptDetail(int deptno) {
		
		return DeptDto.toDto(deptRepo.findById(deptno).get());
	}

}
