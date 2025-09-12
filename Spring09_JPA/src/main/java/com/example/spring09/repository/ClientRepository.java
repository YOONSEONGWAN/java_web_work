package com.example.spring09.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.spring09.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

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
	public List<Client> findAllByOrderByNumDesc();
	public List<Client> findAllByOrderByUserNameDesc();
	
	/*
	 * 	JPQL 문법 형식의 select 문을 직접 작성해서 실행의 결과를 얻어낼 수도 있다. 
	 * 
	 * 	Java Persistence Query Language
	 * 	- sql 처럼 생겼지만 entity 중심으로 작성하는 객체 지향 쿼리 언어
	 * 	- DB 종류에 종속되지 않는다. 
	 */
	@Query("SELECT c FROM Client c ORDER BY c.num DESC")
	public List<Client> findAllQueury(); // 메소드명은 자유 
	
	
	/*
	 * 	특정 DB 에서만 실행될 수 있는 원래의 Query 문도 실행 가능
	 * 	- naviveQuery=true 옵션을 따로 주면 됨
	 */
	@Query(value="SELECT num, userName, createdAt, updatedAt, birthday FROM Client ORDER BY num DESC", nativeQuery = true)
	public List<Client> findAllNativeQuery();
}
