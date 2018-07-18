package com.anpilog.budget.ws.shared.dto;

import java.io.Serializable;

import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;

public class RefreshStatusDTO implements Serializable{

	private static final long serialVersionUID = -3190650120902125964L;
	
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
