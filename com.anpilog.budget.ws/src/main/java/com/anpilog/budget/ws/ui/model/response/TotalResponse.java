package com.anpilog.budget.ws.ui.model.response;

import java.time.LocalDate;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.anpilog.budget.ws.io.entity.ReferenceEntity;
import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

@XmlRootElement 
public class TotalResponse {
	
	private int id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate date;
	private DataRetrievalStatus status;	
	private ReferenceEntity account;
	private Double amount;
	private Double difference;
	private List<ReferenceEntity> transactions;
	
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
	
	public ReferenceEntity getAccount() {
		return account;
	}
	
	public void setAccount(ReferenceEntity account) {
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
	
	public List<ReferenceEntity> getTransactions() {
		return transactions;
	}
	
	public void setTransactions(List<ReferenceEntity> transactions) {
		this.transactions = transactions;
	}
	
}
