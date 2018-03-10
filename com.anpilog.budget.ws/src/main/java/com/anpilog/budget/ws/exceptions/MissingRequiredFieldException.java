package com.anpilog.budget.ws.exceptions;

public class MissingRequiredFieldException extends RuntimeException {

	private static final long serialVersionUID = 2649387441370054864L;

	public MissingRequiredFieldException(String message) {
		super(message);
	}
}
