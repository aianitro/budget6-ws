package com.anpilog.budget.ws.ui.model.response;

import javax.xml.bind.annotation.XmlRootElement;

import com.anpilog.budget.ws.io.entity.ReferenceEntity;

@XmlRootElement 
public class AccountResponse {
	
	private long id;
	private String name;
	private ReferenceEntity bank;
	private Boolean isEnabled;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ReferenceEntity getBank() {
		return bank;
	}
	
	public void setBank(ReferenceEntity bank) {
		this.bank = bank;
	}
	
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}
