package com.anpilog.budget.ws.shared.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import com.anpilog.budget.ws.io.entity.AccountEntity;

public class TotalDTO implements Serializable{

	private static final long serialVersionUID = -33377695509924566L;
	
	private int id;
	private LocalDate date;
	private AccountEntity account;
	private Double amount;
	private Double difference;
	private Set<TransactionDTO> transactions;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public AccountEntity getAccount() {
		return account;
	}
	
	public void setAccount(AccountEntity account) {
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
