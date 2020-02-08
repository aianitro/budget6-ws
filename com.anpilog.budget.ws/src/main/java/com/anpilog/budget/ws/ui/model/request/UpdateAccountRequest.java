package com.anpilog.budget.ws.ui.model.request;

import javax.xml.bind.annotation.XmlRootElement;

import com.anpilog.budget.ws.io.entity.BankEntity;
import com.anpilog.budget.ws.io.entity.enums.AccountType;

@XmlRootElement 
public class UpdateAccountRequest {
	
	private long id;
	private String name;
	private BankEntity bank;
	private String myPortfolioId;
	private Boolean isAutomated;
	private Boolean isEnabled;
	private AccountType accountType;
	
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
	
	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	
}
