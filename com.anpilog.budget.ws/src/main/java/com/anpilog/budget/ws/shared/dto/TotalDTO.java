package com.anpilog.budget.ws.shared.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;

public class TotalDTO implements Serializable{

	private static final long serialVersionUID = -33377695509924566L;
	
	private int id;
	private LocalDate date;
	private DataRetrievalStatus status;
	private AccountDTO account;
	private Double amount;
	private Double previousAmount;
	private Double difference;
	private List<TransactionDTO> transactions;
	
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
	
	public DataRetrievalStatus getStatus() {
		return status;
	}

	public void setStatus(DataRetrievalStatus status) {
		this.status = status;
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
	
	public Double getPreviousAmount() {
		return previousAmount;
	}
	
	public void setPreviousAmount(Double previousAmount) {
		this.previousAmount = previousAmount;
	}
	
	public Double getDifference() {
		return difference;
	}
	
	public void setDifference(Double difference) {
		this.difference = difference;
	}
	
	public List<TransactionDTO> getTransactions() {
		return transactions;
	}
	
	public void setTransactions(List<TransactionDTO> transactions) {
		this.transactions = transactions;
	}
	
}
