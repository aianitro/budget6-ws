package com.anpilog.budget.ws.io.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;


@Entity(name="totals")
public class TotalEntity implements Serializable{

	private static final long serialVersionUID = 8557335726240720816L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Type(type = "date")
	private Date date;
	@ManyToOne
	@JoinColumn(name = "account_id")
	private AccountEntity account;
	private Double amount;
	private Double difference;
	//@Enumerated(EnumType.STRING)
	//@Column(name = "status")
	//private DataRetrievalStatus status;
	//private String errorMessage;
	@OneToMany(mappedBy = "total")
	private List<TransactionEntity> transactions;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
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
