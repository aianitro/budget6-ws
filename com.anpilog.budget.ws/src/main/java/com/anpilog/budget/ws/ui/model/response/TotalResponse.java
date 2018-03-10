package com.anpilog.budget.ws.ui.model.response;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.anpilog.budget.ws.io.entity.AccountEntity;
import com.anpilog.budget.ws.io.entity.TransactionEntity;

@XmlRootElement 
public class TotalResponse {
	
	private int id;
	private Date date;
	private AccountEntity account;
	private Double amount;
	private Double difference;
	private List<TransactionEntity> transactions;
	
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
