package com.anpilog.budget.ws.service;

import java.util.List;

import com.anpilog.budget.ws.shared.dto.SecretQuestionDTO;

public interface SecretQuestionsService {
		
	List<SecretQuestionDTO> getSecurityQuestions();
	SecretQuestionDTO getSecurityQuestion(String id);
	SecretQuestionDTO createSecurityQuestion(SecretQuestionDTO securityQuestionDTO);
	void updateSecurityQuestion(SecretQuestionDTO securityQuestionDTO);
	void deleteSecurityQuestion(SecretQuestionDTO securityQuestionDTO);
	
}
