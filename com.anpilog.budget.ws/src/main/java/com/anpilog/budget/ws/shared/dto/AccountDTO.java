package com.anpilog.budget.ws.shared.dto;

import java.io.Serializable;

import com.anpilog.budget.ws.io.entity.BankEntity;
import com.anpilog.budget.ws.io.entity.enums.BalanceType;

public class AccountDTO implements Serializable{

	private static final long serialVersionUID = -9084439715400864914L;
	
	private long id;
	private String name;
	private BankEntity bank;
	private String myPortfolioId;
	private Boolean isAutomated;
	private Boolean isEnabled;
	private BalanceType balanceType;
	
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
	
	public BalanceType getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(BalanceType balanceType) {
		this.balanceType = balanceType;
	}	
}
