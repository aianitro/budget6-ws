package com.anpilog.budget.ws.ui.model.request;

import javax.xml.bind.annotation.XmlRootElement;

import com.anpilog.budget.ws.io.entity.BankEntity;

@XmlRootElement 
public class CreateAccountRequest {
	
	private String name;
	private BankEntity bank;
	private String myPortfolioId;
	private Boolean isAutomated;
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
	
	public String getMyPortfolioId() {
		return myPortfolioId;
	}
	
	public void setMyPortfolioId(String myPortfolioId) {
		this.myPortfolioId = myPortfolioId;
	}
	
	public Boolean getIsAutomated() {
		return isAutomated;
	}
	
	public void setIsAutomated(Boolean isAutomated) {
		this.isAutomated = isAutomated;
	}
	
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}
