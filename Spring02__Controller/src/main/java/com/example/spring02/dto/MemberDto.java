package com.example.spring02.dto;

import lombok.Getter;
import lombok.Setter;

// lombok 을 이용해서 
@Getter
@Setter
public class MemberDto {
	private int num;
	private String name;
	private String addr;

}
