package com.anpilog.budget.ws.exceptions;

public class NoRecordFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 5411503229638751543L;

	public NoRecordFoundException(String message) {
		super(message);
	}

}
