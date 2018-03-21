package com.anpilog.budget.ws.service.impl;

import java.util.List;

import com.anpilog.budget.ws.exceptions.AccountServiceException;
import com.anpilog.budget.ws.exceptions.CouldNotDeleteRecordException;
import com.anpilog.budget.ws.exceptions.CouldNotUpdateRecordException;
import com.anpilog.budget.ws.exceptions.ExistingReferenceException;
import com.anpilog.budget.ws.exceptions.NoRecordFoundException;
import com.anpilog.budget.ws.io.dao.DAO;
import com.anpilog.budget.ws.service.BanksService;
import com.anpilog.budget.ws.shared.dto.BankDTO;
import com.anpilog.budget.ws.ui.model.response.ErrorMessages;
import com.anpilog.budget.ws.utils.BankUtils;

public class BanksServiceImpl implements BanksService {

	DAO database;
	BankUtils bankUtils = new BankUtils();

	public BanksServiceImpl(DAO database) {
		this.database = database;
	}

	@Override
	public List<BankDTO> getBanks() {

		List<BankDTO> accounts = null;

		try {
			this.database.openConnection();
			accounts = this.database.getBanks();
		} finally {
			this.database.closeConnection();
		}

		return accounts;
	}

	@Override
	public BankDTO getBank(String id) {
		BankDTO returnValue = null;

		try {
			this.database.openConnection();
			returnValue = this.database.getBank(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		} finally {
			this.database.closeConnection();
		}

		return returnValue;
	}

	@Override
	public BankDTO getBankByName(String name) {
		BankDTO bankDto = null;

		if (name == null || name.isEmpty())
			return null;

		try {
			this.database.openConnection();
			bankDto = this.database.getBankByName(name);
		} finally {
			this.database.closeConnection();
		}

		return bankDto;
	}

	@Override
	public BankDTO createBank(BankDTO bankDto) {
		BankDTO returnValue = null;

		// Check if bank already exists
		BankDTO existingAccount = this.getBankByName(bankDto.getName());
		if (existingAccount != null)
			throw new AccountServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.name());

		// Record data into a database
		returnValue = this.saveBank(bankDto);

		// Return back the bank
		return returnValue;
	}

	@Override
	public void updateBank(BankDTO bankDto) {

		// Check if bank already exists
		BankDTO existingAccount = this.getBankByName(bankDto.getName());
		if (existingAccount != null && existingAccount.getId() != bankDto.getId())
			throw new AccountServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.name());

		try {
			this.database.openConnection();
			this.database.updateBank(bankDto);
		} catch (Exception ex) {
			throw new CouldNotUpdateRecordException(ex.getMessage());
		} finally {
			this.database.closeConnection();
		}
	}

	private BankDTO saveBank(BankDTO bankDto) {

		BankDTO returnValue = null;

		try {
			this.database.openConnection();
			returnValue = this.database.saveBank(bankDto);
		} finally {
			this.database.closeConnection();
		}

		return returnValue;
	}

	@Override
	public void deleteBank(BankDTO bankDto) {

		// Check if bank has referenced accounts
		if (bankDto.getAccounts() != null && !bankDto.getAccounts().isEmpty())
			throw new ExistingReferenceException(ErrorMessages.EXISTING_REFERENCES.name());

		try {
			this.database.openConnection();
			this.database.deleteBank(bankDto);
		} catch (Exception ex) {
			throw new CouldNotDeleteRecordException(ex.getMessage());
		} finally {
			this.database.closeConnection();
		}
	}

}
