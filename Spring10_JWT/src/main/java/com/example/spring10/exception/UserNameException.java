package com.example.spring10.exception;

public class UserNameException extends RuntimeException {
	
	public UserNameException(String msg) {
		super(msg); // 전달받아서 부모 생성자에 전달
	}
}
