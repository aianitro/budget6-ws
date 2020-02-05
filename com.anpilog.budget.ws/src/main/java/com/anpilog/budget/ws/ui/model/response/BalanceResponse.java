package com.anpilog.budget.ws.ui.model.response;

import java.time.LocalDate;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.anpilog.budget.ws.io.entity.enums.BalanceType;
import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;
import com.anpilog.budget.ws.ui.model.reference.TotalReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@XmlRootElement 
public class BalanceResponse {
	
	private int id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate date;
	private Double amount;
	private DataRetrievalStatus status;
	private BalanceType balanceType;
	private List<TotalReference> totals;
	
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
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public DataRetrievalStatus getStatus() {
		return status;
	}

	public void setStatus(DataRetrievalStatus status) {
		this.status = status;
	}	

	public BalanceType getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(BalanceType balanceType) {
		this.balanceType = balanceType;
	}

	public List<TotalReference> getTotals() {
		return totals;
	}

	public void setTotals(List<TotalReference> totals) {
		this.totals = totals;
	}
	
}
