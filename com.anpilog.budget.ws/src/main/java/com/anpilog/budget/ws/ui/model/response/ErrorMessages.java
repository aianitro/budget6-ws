package com.anpilog.budget.ws.ui.model.response;

public enum ErrorMessages {
	
	MISSING_REQUIRED_FIELD("Missing required field"),
	RECORD_ALREADY_EXISTS("Record already exists"),
	INTERNAL_SERVER_ERROR("Internal server error"),
	NO_RECORD_FOUND("No record found"),
	AUTHENTICAION_FAILED("Authentication failed"),
	COULD_NOT_UPDATE_RECORD("Could not update"), 
	COULD_NOT_DELETE_RECORD("Could not delete"),
	EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be verified"),
	EXISTING_REFERENCES("Record has dependent references");
	
	private String errorMessage;

	private ErrorMessages(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
