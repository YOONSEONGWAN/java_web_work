package com.example.spring09.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.spring09.entity.Emp;

public interface EmpRepository extends JpaRepository<Emp, Integer>{
	// 사원 이름에 대해서 오름차순 정렬된 결과를 리턴하는 메소드 추가
	public List<Emp> findAllByOrderByEnameAsc(); // 정해진 규칙으로 메소드를 만든다. -> 알아서 동작
	
	// 실행할 query 문 (JPQL) 을  직접 작성 : 은 ? 에 해당
	@Query("SELECT e FROM Emp e WHERE e.dept.deptno = :deptno ORDER BY e.ename ASC")
	public List<Emp> findEmps(@Param("deptno") Integer deptno); // 메소드명은 마음대로 만든다.
	
	// 메소드에 전달된 매개변수의 순서를 이용해서 값을 바인딩할 수도 있다.
	@Query("SELECT e FROM Emp e WHERE e.dept.deptno = ?1 ORDER BY e.ename ASC")
	public List<Emp> findEmps2(Integer deptno); // 메소드명은 마음대로 만든다.
	
	/*
	 * 	Emp entity 에
	 * 
	 * 	@ManyToOne
	 * 	Dept dept; 
	 * 
	 * 	가 있기 때문에 이걸 활용해서 메소드 만들기
	 * 	
	 * 	Dept_Deptno => Emp 의 Dept 필드를 타고 들어가 Dept entity 의 deptno 속성을 조건으로 사용
	 * 	상속 Repository(extends) 가 Emp 이기 때문에 제너릭은 <Emp, Integer> 임. 그러니까 다른 entity 의 필드에 deptno 가 있어도,
	 * 	Emp entity 에서 찾아야함 
	 */
	//정해진 규칙으로 메소드명을 작성해서 위와 같은 결과 얻어내기
	public List<Emp> findByDept_DeptnoOrderByEnameAsc(Integer deptno);
}
