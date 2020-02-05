package com.anpilog.budget.ws.ui.model.request;

import java.time.LocalDate;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.anpilog.budget.ws.io.entity.enums.BalanceType;
import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;
import com.anpilog.budget.ws.ui.model.reference.EntityReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@XmlRootElement 
public class UpdateBalanceRequest {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate date;
	private long id;
	private DataRetrievalStatus status;
	private BalanceType balanceType;
	private List<EntityReference> totals;
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
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

	public List<EntityReference> getTotals() {
		return totals;
	}

	public void setTotals(List<EntityReference> totals) {
		this.totals = totals;
	}
}
