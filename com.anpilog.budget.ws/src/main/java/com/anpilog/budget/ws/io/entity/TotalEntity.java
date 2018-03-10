package com.anpilog.budget.ws.io.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.anpilog.budget.ws.utils.LocalDatePersistenceConverter;


@Entity(name="totals")
public class TotalEntity implements Serializable{

	private static final long serialVersionUID = 8557335726240720816L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Convert(converter = LocalDatePersistenceConverter.class)
	private LocalDate date;
	@ManyToOne
	@JoinColumn(name = "account_id")
	private AccountEntity account;
	private Double amount;
	private Double difference;
	//@Enumerated(EnumType.STRING)
	//@Column(name = "status")
	//private DataRetrievalStatus status;
	//private String errorMessage;
	@OneToMany(mappedBy = "total", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
	
	public AccountEntity getAccount() {
		return account;
	}
	
	public void setAccount(AccountEntity account) {
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
	
}
