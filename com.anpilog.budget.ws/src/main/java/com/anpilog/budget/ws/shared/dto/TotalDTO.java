package com.anpilog.budget.ws.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class TotalDTO implements Serializable{

	private static final long serialVersionUID = -33377695509924566L;
	
	private int id;
	private Date date;
	private AccountDTO account;
	private Double amount;
	private Double difference;
	private Set<TransactionDTO> transactions;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public AccountDTO getAccount() {
		return account;
	}
	
	public void setAccount(AccountDTO account) {
		this.account = account;
	}
	
	public Double getAmount() {
		return amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getDifference() {
		return difference;
	}
	
	public void setDifference(Double difference) {
		this.difference = difference;
	}
	
	public Set<TransactionDTO> getTransactions() {
		return transactions;
	}
	
	public void setTransactions(Set<TransactionDTO> transactions) {
		this.transactions = transactions;
	}
	
}
