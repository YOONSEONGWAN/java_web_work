package com.example.spring09.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.spring09.entity.Member;

/*
 * 	JpaRepository 인터페이스를 상속받은 인터페이스를 정의하는 것 만으로도 구현 클래스가 만들어지고
 * 	해당 클래스로 생성된 객체가 bean 으로 관리가 된다.
 * 	Dao 가 자동으로 만들어진다고 생각하면 된다.
 * 
 * 	extends JpaRepository<Entity class name, data type of PK in Entity(@Id)> Usally, PK is one of int, long, Integer. 
 */

public interface MemberRepository extends JpaRepository<Member, Integer>{
	/*
	 * 	미리 정해진 형식으로 메소드를 만들면 알아서 정렬된다.
	 * 
	 * 	findAllbyOrderByNumDesc()
	 * 	findAllbyOrderByNumAsc()
	 * 	findAllbyOrderByNameDesc()
	 * 	findAllbyOrderByAddrDesc()
	 * 
	 * 	findAllbyOrderBy칼럼명Desc()
	 * 	findAllbyOrderBy칼럼명Asc()
	 * 
	 * 	칼럼명을 CamelCase 로 작성
	 */
	public List<Member> findAllByOrderByNumDesc();
	public List<Member> findAllByOrderByNameDesc();
	
	
	/*
	 * 	JPQL 문법 형식의 select 문을 직접 작성해서 실행의 결과를 얻어낼 수도 있다. 
	 * 
	 * 	Java Persistence Query Language
	 * 	- sql 처럼 생겼지만 entity 중심으로 작성하는 객체 지향 쿼리 언어
	 * 	- DB 종류에 종속되지 않는다. 
	 */
	@Query("SELECT m FROM MEMBER_INFO m ORDER BY m.num DESC")
	public List<Member> findAllQueury(); // 메소드명은 자유 
	
	
	/*
	 * 	특정 DB 에서만 실행될 수 있는 원래의 Query 문도 실행 가능
	 * 	- naviveQuery=true 옵션을 따로 주면 됨
	 */
	@Query(value="SELECT num, name, addr FROM MEMBER_INFO ORDER BY num DESC", nativeQuery = true)
	public List<Member> findAllNativeQuery();
}
