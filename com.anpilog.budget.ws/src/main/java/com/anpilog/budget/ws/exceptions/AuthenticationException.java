package com.anpilog.budget.ws.exceptions;

public class AuthenticationException extends RuntimeException{

	private static final long serialVersionUID = 5832038331858730081L;

	public AuthenticationException(String message) {
		super(message);
	}
}
