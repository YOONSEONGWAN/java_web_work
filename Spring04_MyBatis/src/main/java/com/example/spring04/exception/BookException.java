package com.example.spring04.exception;

public class BookException extends RuntimeException {
	public enum Reason{
		NOT_FOUND, UPDATE_FAILED, DELETE_FAILED
	}
	public final Reason reason;
	
	public BookException(Reason reason, String message) {
		super(message); // 부모생성자에 예외 메시지 전달
		this.reason=reason; // field 에 예외 원인 저장
	}
	public static BookException notFound(int num) {
		return new BookException(Reason.NOT_FOUND, num+"번 책이 없습니다.");
	}
	public static BookException updateFailed(int num) {
		return new BookException(Reason.UPDATE_FAILED, num+"번 책 수정 실패.");
	}
	public static BookException deleteFailed(int num) {
		return new BookException(Reason.DELETE_FAILED, num+"번 책 삭제 실패.");
	}
}
