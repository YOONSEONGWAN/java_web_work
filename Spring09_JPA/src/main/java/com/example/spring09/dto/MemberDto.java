package com.example.spring09.dto;

import com.example.spring09.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Member", description = "회원 정보 Dto")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberDto {
	private Integer num;
	private String name;
	private String addr;
	
	// Entity 를 매개변수로 전달하면, dto 를 리턴하는 static 메소드를 만들기
	public static MemberDto toDto(Member entity) {
		return MemberDto.builder()
				.num(entity.getNum())
				.name(entity.getName())
				.addr(entity.getAddr())
				.build();
	}
	
	// 객체의 필드에 저장된 값을 이용해서 Entity 객체를 만들어 반환하는 non static 메소드
	// static method 가 아니니까 필드값을 쓸 수 있음
	public Member toEntity() {
		return Member.builder()
				.num(this.num ) // null 가능성 있음
				.name(name) // Member Method 안에서 this. 생략 가능
				.addr(addr)
				.build();
	}
}
