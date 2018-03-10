package com.anpilog.budget.ws.ui.model.request;

import javax.xml.bind.annotation.XmlRootElement;

import com.anpilog.budget.ws.io.entity.BankEntity;

@XmlRootElement 
public class UpdateAccountRequest {
	
	private String name;
	private BankEntity bank;
	private Boolean isEnabled;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public BankEntity getBank() {
		return bank;
	}
	
	public void setBank(BankEntity bank) {
		this.bank = bank;
	}
	
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}
