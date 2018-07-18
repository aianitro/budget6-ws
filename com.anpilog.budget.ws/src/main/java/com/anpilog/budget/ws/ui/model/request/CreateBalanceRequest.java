package com.anpilog.budget.ws.ui.model.request;

import java.time.LocalDate;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.anpilog.budget.ws.io.entity.TotalEntity;
import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

@XmlRootElement 
public class CreateBalanceRequest {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate date;
	private DataRetrievalStatus status;
	private List<TotalEntity> totals;
	
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

	public List<TotalEntity> getTotals() {
		return totals;
	}

	public void setTotals(List<TotalEntity> totals) {
		this.totals = totals;
	}
}
