package com.example.spring09.dto;

import lombok.Data;

@Data
public class LoginRequest { // login 요청할 때 사용하는 dto
	private String userName;
	private String password;
}
