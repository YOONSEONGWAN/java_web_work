package com.example.spring04.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("bookDto")
@Getter
@Setter
public class BookDto {
	private int num;
	private String name;
	private String author;
	private String publisher;
}
