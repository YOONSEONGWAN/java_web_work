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

	@Transactional(readOnly = true)
	@Override
	public List<EmpDto> getEmpListByDeptno(int deptno) {
		List<EmpDto> empList1=empRepo.findEmps(deptno).stream().map(EmpDto::toDto).toList();
		List<EmpDto> empList2=empRepo.findEmps2(deptno).stream().map(EmpDto::toDto).toList();
		List<EmpDto> empList3=empRepo.findByDept_DeptnoOrderByEnameAsc(deptno).stream().map(EmpDto::toDto).toList();
		/*
		 * 	서비스에서는 Repository 메소드를 호출하여 사용	
		 * 
		 * 	1. Repository 의 메소드를 호출한다 => entity 리턴
		 * 	2. stream 으로 바꾼다
		 * 	3. map 으로 dto 로 바꿔준다.
		 * 	4. toList 로 List 객체로 바꿔줌
		 */
		
		
		return empList3;
	}

}
