package com.anpilog.budget.ws.shared.dto;

import java.io.Serializable;
import java.util.Set;

public class BankDTO implements Serializable{

	private static final long serialVersionUID = -8164785908504117434L;
	
	private long id;
	private String name;
	private Boolean isEnabled;
	private Set<SecretQuestionDTO> secretQuestions;	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Set<SecretQuestionDTO> getSecretQuestions() {
		return secretQuestions;
	}

	public void setSecretQuestions(Set<SecretQuestionDTO> secretQuestions) {
		this.secretQuestions = secretQuestions;
	}
	
}
