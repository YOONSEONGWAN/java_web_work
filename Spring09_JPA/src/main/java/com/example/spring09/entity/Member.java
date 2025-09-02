package com.example.spring09.entity;


import com.example.spring09.dto.MemberDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// entity 클래스에는 @Data 어노테이션을 붙일 수 없다. 붙이면 문제 발생함
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity(name="MEMBER_INFO")
public class Member {
	// num 이라는 필드에 대해서 2개의 어노테이션 추가 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer num;
	private String name;
	private String addr;
		
}
