package com.example.spring09.service;

import java.util.List;

import com.example.spring09.dto.DeptDto;
import com.example.spring09.dto.EmpDeptDto;
import com.example.spring09.dto.EmpDto;

public interface EmployService {
	public List<EmpDto> getEmpList();
	public List<DeptDto> getDeptList();
	public EmpDeptDto getEmpDetail(int empno);
	public DeptDto getDeptDetail(int deptno);
	
}
