package com.aim.exception;

@SuppressWarnings("serial")
public class NotAuthException extends RuntimeException{
	
	public NotAuthException() {
	}
	
	public NotAuthException(String message) {
		super(message);
	}

}
