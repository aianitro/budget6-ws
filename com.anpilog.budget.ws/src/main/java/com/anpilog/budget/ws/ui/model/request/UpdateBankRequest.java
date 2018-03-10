package com.anpilog.budget.ws.ui.model.request;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.anpilog.budget.ws.io.entity.SecretQuestionEntity;
import com.anpilog.budget.ws.io.entity.UILoginDetailsEntity;

@XmlRootElement 
public class UpdateBankRequest {
	
	private long id;
	private String name;
	private Boolean isEnabled;
	private Set<SecretQuestionEntity> secretQuestions;	
	private UILoginDetailsEntity uiLoginDetails;
	
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
	
	public UILoginDetailsEntity getUiLoginDetails() {
		return uiLoginDetails;
	}
	
	public void setUiLoginDetails(UILoginDetailsEntity uiLoginDetails) {
		this.uiLoginDetails = uiLoginDetails;
	}
}
