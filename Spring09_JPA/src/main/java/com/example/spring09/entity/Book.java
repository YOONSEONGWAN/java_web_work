package com.example.spring09.entity;

import com.example.spring09.dto.BookDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity(name = "BOOK_INFO")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String author;
	private String publisher;
	
	public static Book toEntity(BookDto dto) {
		return Book.builder()
				.id(dto.getId()== 0 ? null : dto.getId())
				.name(dto.getName())
				.author(dto.getAuthor())
				.publisher(dto.getPublisher())
				.build();
				
	}
}
