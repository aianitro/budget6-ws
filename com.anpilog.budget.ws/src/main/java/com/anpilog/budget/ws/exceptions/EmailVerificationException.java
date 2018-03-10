package com.anpilog.budget.ws.exceptions;

public class EmailVerificationException extends RuntimeException {

    private static final long serialVersionUID = 6111953689856500369L;
    
    public EmailVerificationException(String message)
    {
        super(message);
    }
    
}
