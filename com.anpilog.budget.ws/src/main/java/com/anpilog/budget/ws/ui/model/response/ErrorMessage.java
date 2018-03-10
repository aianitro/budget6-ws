package com.anpilog.budget.ws.ui.model.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {
	
	private String ErrorMessage;
	private String ErrorMessageKey;
	
	public ErrorMessage() {}
	
	public ErrorMessage(String errorMessage, String errorMessageKey) {
		ErrorMessage = errorMessage;
		ErrorMessageKey = errorMessageKey;
	}

	public String getErrorMessage() {
		return ErrorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		ErrorMessage = errorMessage;
	}
	
	public String getErrorMessageKey() {
		return ErrorMessageKey;
	}
	
	public void setErrorMessageKey(String errorMessageKey) {
		ErrorMessageKey = errorMessageKey;
	}

}
