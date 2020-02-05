package com.anpilog.budget.ws.ui.model.reference;

import java.time.LocalDate;

import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;

public class TotalReference implements Comparable<Object>{
	
	private int id;
	private LocalDate date;
	private DataRetrievalStatus status;	
	private EntityReference account;
	private Double amount;
	private Double difference;
	private Integer transactions;
	
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
	
	public EntityReference getAccount() {
		return account;
	}
	
	public void setAccount(EntityReference account) {
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
	
	public Integer getTransactions() {
		return transactions;
	}
	
	public void setTransactions(Integer transactions) {
		this.transactions = transactions;
	}
	
	@Override
	public int compareTo(Object o) {
		return account.getId().compareTo(((TotalReference) o).getAccount().getId());
	}

}
