package com.anpilog.budget.ws.ui.model.request;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public class RefreshRequest {
	
	private Boolean runBankAccounts;
	private Boolean runCreditScore;
	
	public Boolean getRunBankAccounts() {
		return runBankAccounts;
	}
	
	public void setRunBankAccounts(Boolean runBankAccounts) {
		this.runBankAccounts = runBankAccounts;
	}
	
	public Boolean getRunCreditScore() {
		return runCreditScore;
	}
	
	public void setRunCreditScore(Boolean runCreditScore) {
		this.runCreditScore = runCreditScore;
	}
	
}
