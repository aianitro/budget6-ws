package com.anpilog.budget.ws.shared.dto;

import java.io.Serializable;

import com.anpilog.budget.ws.ui.model.response.RefreshStatuses;

public class RefreshStatusDTO implements Serializable{

	private static final long serialVersionUID = -3190650120902125964L;
	
	private RefreshStatuses status;
	private String details;
	
	public RefreshStatuses getStatus() {
		return status;
	}
	
	public void setStatus(RefreshStatuses status) {
		this.status = status;
	}
	
	public String getDetails() {
		return details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
}
