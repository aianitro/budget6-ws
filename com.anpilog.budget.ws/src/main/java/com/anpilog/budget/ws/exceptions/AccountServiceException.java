package com.anpilog.budget.ws.exceptions;

public class AccountServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 1917287533166699171L;

	public AccountServiceException(String message) {
		super(message);
	}
}
