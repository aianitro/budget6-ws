package com.anpilog.budget.ws.io.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;


@Entity(name="transactions")
public class TransactionEntity implements Serializable{

	private static final long serialVersionUID = -2286639616073388035L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Type(type = "date")
	private Date date;
	@ManyToOne
	@JoinColumn(name = "total_id")
	private TotalEntity total;
	private String decription;
	private double amount;
	private String categoryStr;
	private TransactionEntity transferPair;
	/*
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;	
	@ManyToOne
	@JoinColumn(name = "categorization_rule_id")
	private CategorizationRule categorizationRule;
	*/
	
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
	
	public TotalEntity getTotal() {
		return total;
	}
	
	public void setTotal(TotalEntity total) {
		this.total = total;
	}
	
	public String getDecription() {
		return decription;
	}
	
	public void setDecription(String decription) {
		this.decription = decription;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getCategoryStr() {
		return categoryStr;
	}
	
	public void setCategoryStr(String categoryStr) {
		this.categoryStr = categoryStr;
	}
	
	public TransactionEntity getTransferPair() {
		return transferPair;
	}
	
	public void setTransferPair(TransactionEntity transferPair) {
		this.transferPair = transferPair;
	}	
	
}
