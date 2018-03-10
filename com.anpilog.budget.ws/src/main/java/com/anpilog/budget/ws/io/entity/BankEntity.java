package com.anpilog.budget.ws.io.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="banks")
public class BankEntity implements Serializable{

	private static final long serialVersionUID = -1451477060558730113L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private Boolean isEnabled;
	@OneToMany(mappedBy = "bank", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<SecretQuestionEntity> secretQuestions;	
	@OneToMany(mappedBy = "bank", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<AccountEntity> accounts;	
	/*
	@OneToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "ui_login_details_id", unique = true)
	private UILoginDetailsEntity uiLoginDetails;
	*/
	
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

	
	public Set<SecretQuestionEntity> getSecretQuestions() {
		return secretQuestions;
	}

	public void setSecretQuestions(Set<SecretQuestionEntity> secretQuestions) {
		this.secretQuestions = secretQuestions;
	}
	
	public Set<AccountEntity> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<AccountEntity> accounts) {
		this.accounts = accounts;
	}
	
	
	/*
	public UILoginDetailsEntity getUiLoginDetails() {
		return uiLoginDetails;
	}

	public void setUiLoginDetails(UILoginDetailsEntity uiLoginDetails) {
		this.uiLoginDetails = uiLoginDetails;
	}
	*/
	
}
