package com.anpilog.budget.ws.ui.model.request;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public class UpdateSecurityQuestionRequest {
	
	private String question;
	private String answer;
	
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
