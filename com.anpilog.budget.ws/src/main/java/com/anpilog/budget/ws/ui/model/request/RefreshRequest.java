package com.anpilog.budget.ws.ui.model.request;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public class RefreshRequest {
	
	private Boolean isRunningBankAccounts;
	private Boolean isRunningCreditScore;
	
	public Boolean getIsRunningBankAccounts() {
		return isRunningBankAccounts;
	}
	
	public void setIsRunningBankAccounts(Boolean isRunningBankAccounts) {
		this.isRunningBankAccounts = isRunningBankAccounts;
	}
	
	public Boolean getIsRunningCreditScore() {
		return isRunningCreditScore;
	}
	
	public void setIsRunningCreditScore(Boolean isRunningCreditScore) {
		this.isRunningCreditScore = isRunningCreditScore;
	}

}
