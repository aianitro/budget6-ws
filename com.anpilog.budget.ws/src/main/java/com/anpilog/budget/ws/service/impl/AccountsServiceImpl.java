package com.anpilog.budget.ws.service.impl;

import java.util.List;

import com.anpilog.budget.ws.exceptions.AccountServiceException;
import com.anpilog.budget.ws.exceptions.CouldNotDeleteRecordException;
import com.anpilog.budget.ws.exceptions.CouldNotUpdateRecordException;
import com.anpilog.budget.ws.exceptions.NoRecordFoundException;
import com.anpilog.budget.ws.io.dao.DAO;
import com.anpilog.budget.ws.service.AccountsService;
import com.anpilog.budget.ws.shared.dto.AccountDTO;
import com.anpilog.budget.ws.ui.model.response.ErrorMessages;
import com.anpilog.budget.ws.utils.AccountUtils;

public class AccountsServiceImpl implements AccountsService {

	DAO database;
	AccountUtils accountUtils = new AccountUtils();

	public AccountsServiceImpl(DAO database) {
		this.database = database;
	}

	@Override
	public List<AccountDTO> getAccounts(int start, int limit) {
		
		List<AccountDTO> accounts = null;
		
		try {
			this.database.openConnection();
			accounts = this.database.getAccounts(start, limit);
		} finally {
			this.database.closeConnection();
		}
		
		return accounts;
	}
	
	@Override
	public List<AccountDTO> getAccountsEnabledOnly(int start, int limit) {
		
		List<AccountDTO> accounts = null;
		
		try {
			this.database.openConnection();
			accounts = this.database.getAccountsEnabledOnly(start, limit);
		} finally {
			this.database.closeConnection();
		}
		
		return accounts;
	}

	@Override
	public AccountDTO getAccount(String id) {
		AccountDTO returnValue = null;

		try {
			this.database.openConnection();
			returnValue = this.database.getAccount(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		} finally {
			this.database.closeConnection();
		}

		return returnValue;
	}

	@Override
	public AccountDTO getAccountByName(String name) {
		AccountDTO accountDto = null;

		if (name == null || name.isEmpty())
			return null;

		try {
			this.database.openConnection();
			accountDto = this.database.getAccountByName(name);
		} finally {
			this.database.closeConnection();
		}

		return accountDto;
	}

	@Override
	public AccountDTO createAccount(AccountDTO accountDto) {
		AccountDTO returnValue = null;

		// Validate the required fields
		accountUtils.validateRequiredFields(accountDto);

		// Check if account already exists
		AccountDTO existingAccount = this.getAccountByName(accountDto.getName());
		if (existingAccount != null)
			throw new AccountServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.name());

		// Record data into a database
		returnValue = this.saveAccount(accountDto);

		// Return back the account
		return returnValue;
	}
	

	@Override
	public void updateAccountDetails(AccountDTO accountDto) {
		// Validate the required fields
		accountUtils.validateRequiredFields(accountDto);

		try {
			this.database.openConnection();
			this.database.updateAccount(accountDto);
		}catch(Exception ex) {
			throw new CouldNotUpdateRecordException(ex.getMessage());
		} finally {
			this.database.closeConnection();
		}
	}

	private AccountDTO saveAccount(AccountDTO accountDto) {

		AccountDTO returnValue = null;

		try {
			this.database.openConnection();
			returnValue = this.database.saveAccount(accountDto);
		} finally {
			this.database.closeConnection();
		}

		return returnValue;
	}

	@Override
	public void deleteAccount(AccountDTO accountDto) {
		try {
			this.database.openConnection();
			this.database.deleteAccount(accountDto);
		}catch(Exception ex) {
			throw new CouldNotDeleteRecordException(ex.getMessage());
		} finally {
			this.database.closeConnection();
		}
	}

}
