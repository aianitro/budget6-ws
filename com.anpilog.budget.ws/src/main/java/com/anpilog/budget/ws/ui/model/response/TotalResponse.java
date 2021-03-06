package com.anpilog.budget.ws.ui.model.response;

import java.time.LocalDate;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.anpilog.budget.ws.io.entity.TransactionEntity;
import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;
import com.anpilog.budget.ws.ui.model.reference.EntityReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@XmlRootElement 
public class TotalResponse implements Comparable<Object>{
	
	private int id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate date;
	private DataRetrievalStatus status;	
	private EntityReference account;
	private Double amount;
	private Double difference;
	private List<TransactionEntity> transactions;
	
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
	
	public List<TransactionEntity> getTransactions() {
		return transactions;
	}
	
	public void setTransactions(List<TransactionEntity> transactions) {
		this.transactions = transactions;
	}

	@Override
	public int compareTo(Object o) {
		return account.getId().compareTo(((TotalResponse) o).getAccount().getId());
	}
	
}
