package com.anpilog.budget.ws.service.impl;

import java.util.List;

import com.anpilog.budget.ws.io.dao.DAO;
import com.anpilog.budget.ws.service.SecretQuestionsService;
import com.anpilog.budget.ws.shared.dto.SecretQuestionDTO;

public class SecretQuestionsServiceImpl implements SecretQuestionsService {

	DAO database;

	public SecretQuestionsServiceImpl(DAO database) {
		this.database = database;
	}

	@Override
	public List<SecretQuestionDTO> getSecretQuestions() {
		
		List<SecretQuestionDTO> secretQuestion = null;
		
		try {
			this.database.openConnection();
			secretQuestion = this.database.getSecretQuestions();
		} finally {
			this.database.closeConnection();
		}
		
		return secretQuestion;
	}
	
}
