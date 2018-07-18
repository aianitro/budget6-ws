package com.anpilog.budget.ws.io.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;
import com.anpilog.budget.ws.utils.LocalDatePersistenceConverter;


@Entity(name="balances")
public class BalanceEntity implements Serializable{

	private static final long serialVersionUID = 7501350603896099294L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Convert(converter = LocalDatePersistenceConverter.class)
	private LocalDate date;
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private DataRetrievalStatus status;
	//private String errorMessage;
	@OneToMany(mappedBy = "balance", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<TotalEntity> totals;
	
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

	public List<TotalEntity> getTotals() {
		return totals;
	}

	public void setTotals(List<TotalEntity> totals) {
		this.totals = totals;
	}
	
}
