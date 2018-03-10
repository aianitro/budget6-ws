package com.anpilog.budget.ws.io.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="secret_questions")
public class SecretQuestionEntity implements Serializable{
	
	private static final long serialVersionUID = -7762107983713810871L;
	
	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
	@JoinColumn(name = "bank_id", nullable = false)
	private BankEntity bank;
	private String question;
	private String answer;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public BankEntity getBank() {
		return bank;
	}

	public void setBank(BankEntity bank) {
		this.bank = bank;
	}

	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
		
}
