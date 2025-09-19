package com.example.spring10.exception;

// 기본 비밀번호가 일치하지 않았을 때 throw 할 예외 class
public class PasswordException extends RuntimeException {

	public PasswordException(String msg) {
		super(msg);
	}
}
