package com.anpilog.budget.ws.ui.model.request;

import java.time.LocalDate;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.anpilog.budget.ws.io.entity.AccountEntity;
import com.anpilog.budget.ws.io.entity.TransactionEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@XmlRootElement 
public class CreateTotalRequest {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate date;
	private AccountEntity account;
	private Double amount;
	private Double difference;
	private List<TransactionEntity> transactions;
	
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
	
	public List<TransactionEntity> getTransactions() {
		return transactions;
	}
	
	public void setTransactions(List<TransactionEntity> transactions) {
		this.transactions = transactions;
	}
}
