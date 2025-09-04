package com.example.spring09.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Emp {
	@Id
	private Integer empno;
	private String ename;
	private String job;
	private Integer mgr;
	private LocalDate hiredate;
	private Double sal;
	private Double comm;
	// private Integer dempno; 
	// dept entity 의 deptno 를 참조
	
	// Entity 안에 또 다른 Entity 가 있으면 편리하지 않을까
	
	/*
	 * 	Emp 객체 하나는 사원 한 명의 정보를 가지고 있다.
	 * 	Dept 객체 하나는 부서 하나의 정보를 가지고 있다.
	 * 	Emp 객체 안에 있는 Dept 객체는 Emp 객체가 가지고 있는 해당 사원의 부서 정보를 가지게 하고 싶다 ! ! !
	 * 	
	 * 	name="deptno" 는 Emp 테이블의 칼럼명을 결정한다.
	 * 	referencedColumnName = "deptno" Dept 테이블의 어떤 칼럼을 참조할지 결정한다(생략시 자동으로 @Id 칼럼 참조)
	 */
	@ManyToOne
	@JoinColumn(name="deptno", referencedColumnName = "deptno")
	private Dept dept;
}
