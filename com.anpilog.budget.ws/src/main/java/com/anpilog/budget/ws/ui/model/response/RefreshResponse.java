package com.anpilog.budget.ws.ui.model.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RefreshResponse {

	private RefreshStatuses refreshStatus;
	private String details;

	public RefreshStatuses getStatus() {
		return refreshStatus;
	}

	public void setStatus(RefreshStatuses refreshStatus) {
		this.refreshStatus = refreshStatus;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
