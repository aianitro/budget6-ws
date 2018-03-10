package com.anpilog.budget.ws.shared.dto;

import java.io.Serializable;

public class SecretQuestionDTO implements Serializable{

	private static final long serialVersionUID = 3127372106774385260L;
	
	private long id;
	private String question;
	private String answer;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
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
