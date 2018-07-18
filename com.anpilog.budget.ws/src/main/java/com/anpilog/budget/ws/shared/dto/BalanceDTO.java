package com.anpilog.budget.ws.shared.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;

public class BalanceDTO implements Serializable{
	
	private static final long serialVersionUID = -6011010857160810390L;
	
	private int id;
	private LocalDate date;
	private DataRetrievalStatus status;
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

	public DataRetrievalStatus getStatus() {
		return status;
	}

	public void setStatus(DataRetrievalStatus status) {
		this.status = status;
	}

	public List<TotalDTO> getTotals() {
		return totals;
	}

	public void setTotals(List<TotalDTO> totals) {
		this.totals = totals;
	}
	
}
