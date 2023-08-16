package com.example.libraryeventlistener.exception;

public class MaxRetryAttemptsExceededException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MaxRetryAttemptsExceededException (String message) {
		super(message);
	}

}
