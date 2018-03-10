package com.anpilog.budget.ws.shared.dto;

import java.io.Serializable;

import com.anpilog.budget.ws.io.entity.BankEntity;

public class AccountDTO implements Serializable{

	private static final long serialVersionUID = -9084439715400864914L;
	
	private long id;
	private String name;
	private BankEntity bank;
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
