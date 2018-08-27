package com.anpilog.budget.ws.ui.model.response;

import java.time.LocalDate;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.anpilog.budget.ws.io.entity.TotalEntity;
import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

@XmlRootElement 
public class BalanceResponse {
	
	private int id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate date;
	private DataRetrievalStatus status;
	private List<TotalResponse> totals;
	
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

	public List<TotalResponse> getTotals() {
		return totals;
	}

	public void setTotals(List<TotalResponse> totals) {
		this.totals = totals;
	}
	
}
