package com.aim.exception;

@SuppressWarnings("serial")
public class NotFindMemberException extends RuntimeException{
	
	public NotFindMemberException() {
	}
	
	public NotFindMemberException(String message) {
		super(message);
	}
}
