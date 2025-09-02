package com.example.spring09.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.spring09.entity.Client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientDto {
	private Long num;
	
	@NotBlank(message="이름은 필수 입니다.")
	@Size(max=20, message="이름은 최대 20자 까지 가능합니다.")
	private String userName;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	/*
	 * 	@Past
	 * 	@PastOrPresent
	 * 	@Future
	 * 	@FutureOrPresent
	 * 	중에 하나로 검증
	 */
	@PastOrPresent(message="미래에 태어날 순 없습니다.")
	private LocalDate birthday;
	
	// static toDto method
	public static ClientDto toDto(Client entity) {
		return ClientDto.builder()
				.num(entity.getNum())
				.userName(entity.getUserName())
				.createdAt(entity.getCreatedAt())
				.updatedAt(entity.getUpdatedAt())
				.birthday(entity.getBirthday())
				.build();
		
	}
	// non static toEntity method
	public Client toEntity() {
		return Client.builder()
				.num(this.num)
				.userName(userName)
				.createdAt(createdAt)
				.updatedAt(updatedAt)
				.birthday(birthday)
				.build();
		}
}
