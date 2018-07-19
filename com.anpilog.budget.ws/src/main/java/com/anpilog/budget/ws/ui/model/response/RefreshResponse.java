package com.anpilog.budget.ws.ui.model.response;

import javax.xml.bind.annotation.XmlRootElement;

import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;

@XmlRootElement
public class RefreshResponse {

	private DataRetrievalStatus status;
	private String details;

	public DataRetrievalStatus getStatus() {
		return status;
	}

	public void setStatus(DataRetrievalStatus status) {
		this.status = status;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
