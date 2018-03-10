package com.anpilog.budget.ws.service.impl;

import java.util.List;

import com.anpilog.budget.ws.exceptions.CouldNotDeleteRecordException;
import com.anpilog.budget.ws.exceptions.CouldNotUpdateRecordException;
import com.anpilog.budget.ws.exceptions.NoRecordFoundException;
import com.anpilog.budget.ws.io.dao.DAO;
import com.anpilog.budget.ws.io.dao.impl.MySQLDAO;
import com.anpilog.budget.ws.service.SecretQuestionsService;
import com.anpilog.budget.ws.shared.dto.SecretQuestionDTO;
import com.anpilog.budget.ws.ui.model.response.ErrorMessages;
import com.anpilog.budget.ws.utils.AccountUtils;

public class SecretQuestionsServiceImpl implements SecretQuestionsService {

	DAO database;
	AccountUtils accountUtils = new AccountUtils();

	public SecretQuestionsServiceImpl() {
		this.database = new MySQLDAO();
	}

	@Override
	public List<SecretQuestionDTO> getSecurityQuestions() {
		
		List<SecretQuestionDTO> returnValue = null;
		
		try {
			this.database.openConnection();
			returnValue = this.database.getSecretQuestions();
		} finally {
			this.database.closeConnection();
		}
		
		return returnValue;
	}

	@Override
	public SecretQuestionDTO getSecurityQuestion(String id) {
		SecretQuestionDTO returnValue = null;

		try {
			this.database.openConnection();
			returnValue = this.database.getSecretQuestion(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		} finally {
			this.database.closeConnection();
		}

		return returnValue;
	}

	@Override
	public SecretQuestionDTO createSecurityQuestion(SecretQuestionDTO securityQuestionDto) {
		SecretQuestionDTO returnValue = null;

		// Record data into a database
		returnValue = this.saveSecurityQuestion(securityQuestionDto);

		// Return back the account
		return returnValue;
	}
	

	@Override
	public void updateSecurityQuestion(SecretQuestionDTO securityQuestionDto) {

		try {
			this.database.openConnection();
			this.database.updateSecretQuestion(securityQuestionDto);
		}catch(Exception ex) {
			throw new CouldNotUpdateRecordException(ex.getMessage());
		} finally {
			this.database.closeConnection();
		}
	}

	private SecretQuestionDTO saveSecurityQuestion(SecretQuestionDTO securityQuestionDto) {

		SecretQuestionDTO returnValue = null;

		try {
			this.database.openConnection();
			returnValue = this.database.saveSecretQuestion(securityQuestionDto);
		} finally {
			this.database.closeConnection();
		}

		return returnValue;
	}

	@Override
	public void deleteSecurityQuestion(SecretQuestionDTO securityQuestionDto) {
		try {
			this.database.openConnection();
			this.database.deleteSecretQuestion(securityQuestionDto);
		}catch(Exception ex) {
			throw new CouldNotDeleteRecordException(ex.getMessage());
		} finally {
			this.database.closeConnection();
		}
	}

}
