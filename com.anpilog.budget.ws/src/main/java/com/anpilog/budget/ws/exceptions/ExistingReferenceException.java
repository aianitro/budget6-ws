package com.anpilog.budget.ws.exceptions;

public class ExistingReferenceException extends RuntimeException {
	
	private static final long serialVersionUID = 1917287533166699171L;

	public ExistingReferenceException(String message) {
		super(message);
	}
}
