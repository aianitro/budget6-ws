package com.anpilog.budget.ws.shared.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class TransactionDTO implements Serializable{

	private static final long serialVersionUID = 5490726788823406474L;
	
	private int id;
	private LocalDate date;
	private String decription;
	private double amount;
	private String categoryStr;
	private TransactionDTO transferPair;
	
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
	
	public String getDecription() {
		return decription;
	}
	
	public void setDecription(String decription) {
		this.decription = decription;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getCategoryStr() {
		return categoryStr;
	}
	
	public void setCategoryStr(String categoryStr) {
		this.categoryStr = categoryStr;
	}
	
	public TransactionDTO getTransferPair() {
		return transferPair;
	}
	
	public void setTransferPair(TransactionDTO transferPair) {
		this.transferPair = transferPair;
	}
	
}
