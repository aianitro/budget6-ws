package com.anpilog.budget.ws.io.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="accounts")
public class AccountEntity implements Serializable{
	
	private static final long serialVersionUID = -7588692817458628147L;

	@Id
	@GeneratedValue
	private long id;
	private String name;
	@ManyToOne
	@JoinColumn(name = "bank_id", nullable = false)	
	private BankEntity bank;
	private Boolean isEnabled;
	private String accountId;
	
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
	
	public String getAccountId() {
		return accountId;
	}
	
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
}
