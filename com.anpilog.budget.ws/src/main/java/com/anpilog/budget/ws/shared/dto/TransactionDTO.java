package com.anpilog.budget.ws.shared.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class TransactionDTO implements Serializable{

	private static final long serialVersionUID = 5490726788823406474L;
	
	private int id;
	private TotalDTO total;
	private LocalDate date;
	private String description;
	private double amount;
	private String categoryStr;
	private TransactionDTO transferPair;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public TotalDTO getTotal() {
		return total;
	}
	
	public void setTotal(TotalDTO total) {
		this.total = total;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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
