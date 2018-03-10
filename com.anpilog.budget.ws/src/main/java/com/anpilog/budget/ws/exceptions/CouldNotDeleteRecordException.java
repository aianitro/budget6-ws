package com.anpilog.budget.ws.exceptions;

public class CouldNotDeleteRecordException extends RuntimeException{

	private static final long serialVersionUID = -2654497017405752873L;
	
	public CouldNotDeleteRecordException(String message) {
		super(message);
	}

}
