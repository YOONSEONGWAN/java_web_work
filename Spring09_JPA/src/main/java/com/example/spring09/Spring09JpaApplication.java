package com.example.spring09;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.spring09.entity.Book;
import com.example.spring09.entity.Client;
import com.example.spring09.entity.Member;
import com.example.spring09.repository.BookRepository;
import com.example.spring09.repository.MemberRepository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

@SpringBootApplication
public class Spring09JpaApplication {
	/*
	 * 	spring data JPA 를 dependency 로 추가하고 application.properties 파일에 적절한 jpa 설정을 해두면
	 * 	EntityManagerFactory 가 Bean 으로 관리된다.
	 */
	// 의존객체 주입 받기
	@Autowired
	EntityManagerFactory emf;
	
	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	BookRepository bookRepo;
	
	@PostConstruct
	public void bookJPA() {
		EntityManager em=emf.createEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		try {
			Book b1=Book.builder().name("책 먹는 여우").author("여우").build();
			Book b2=Book.builder().name("책 먹는 사자").author("사자").build();
			Book b3=Book.builder().name("책 먹는 기린").author("기린").build();
			
			em.persist(b1);
			em.persist(b2);
			em.persist(b3);
			tx.commit();
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			em.close();
		}
		
		
		List<Book> list= bookRepo.findAll();
		for(Book tmp : list) {
			System.out.println(tmp.getId() +"| "+ tmp.getName() +" | "+ tmp.getAuthor() +" | "+ tmp.getPublisher() );
		}
	}
	
	@PostConstruct
	public void helloJPA() {
		// EntityManager 객체를 얻어와서
		EntityManager em=emf.createEntityManager();
		// 트랜잭션을 시작한다.
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		try {
			// Entity 객체를 생성하고 
			Member m1=Member.builder().name("김구라").addr("노량진").build();
			Member m2=Member.builder().name("해골").addr("행신동").build();
			Member m3=Member.builder().name("원숭이").addr("동물원").build();
			// Client Sample data 입력
			Client c1=Client.builder().userName("김구라").build();
			Client c2=Client.builder().userName("해골").build();
			Client c3=Client.builder().userName("원숭이").build();
			
			
			// EntityManager 객체의 prtsist() 메소드를 이용해 객체에 저장된 정보를 
			// 영구저장 할 수 있다. 
			em.persist(m1);
			em.persist(m2);
			em.persist(m3);
			em.persist(c1);
			em.persist(c2);
			em.persist(c3);
			tx.commit(); // commit 하는 시점에 저장
		}catch (Exception e) {
			e.printStackTrace();
			tx.rollback(); // 예외가 발생한 경우 저장하지 않고 rollback 할 수 있음. 
		}finally {
			em.close();
		}
		
		// select 도 해보기
		EntityManager em2=emf.createEntityManager();
		/*
		 * 	MEMBER_INFO 라는 테이블의 별칭을 m 이라고 하고 
		 * 	m 의 모든 칼럼 정보를 select 해서 Member.class type 객체에 담겠다는 뜻 
		 * 	row 가 여러개인 경우 .getResultList() 메소드를 호출해서 결과를 얻어낸다.
		 * 	Member.class type 을 전달했기 때문에 List 의 제너릭은 <Member> type. 
		 */
		List<Member> members=em2.createQuery("SELECT m FROM MEMBER_INFO m", Member.class)
								.getResultList();
		for (Member mem : members) {
			System.out.println(mem.getNum() +": "+ mem.getName() +" / "+ mem.getAddr());
		}
		em2.close();
		
		// MemberRepository 객체를 이용해 select 하기
		List<Member> list= memberRepo.findAll();
		for(Member tmp : list) {
			System.out.println(tmp.getNum() +"| "+ tmp.getName() +" | "+ tmp.getAddr());
		}
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Spring09JpaApplication.class, args);
	}

}
