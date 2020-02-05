package com.anpilog.budget.ws.shared.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.anpilog.budget.ws.io.entity.enums.BalanceType;
import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;

public class BalanceDTO implements Serializable{
	
	private static final long serialVersionUID = -6011010857160810390L;
	
	private int id;
	private LocalDate date;
	private Double amount;
	private DataRetrievalStatus status;
	private BalanceType balanceType;
	private List<TotalDTO> totals;
	
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

	public List<TotalDTO> getTotals() {
		return totals;
	}

	public void setTotals(List<TotalDTO> totals) {
		this.totals = totals;
	}
	
}
