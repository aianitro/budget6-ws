package com.anpilog.budget.ws.ui.model.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeleteAccountResponse {
	private RequestOperation requestOperation;
	private ResponseStatus responseStatus;

	public RequestOperation getRequestOperation() {
		return requestOperation;
	}

	public void setRequestOperation(RequestOperation requestOperation) {
		this.requestOperation = requestOperation;
	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

}
