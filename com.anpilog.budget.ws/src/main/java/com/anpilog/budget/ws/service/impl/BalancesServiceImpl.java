package com.anpilog.budget.ws.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import com.anpilog.budget.ws.exceptions.CouldNotUpdateRecordException;
import com.anpilog.budget.ws.exceptions.NoRecordFoundException;
import com.anpilog.budget.ws.io.dao.DAO;
import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;
import com.anpilog.budget.ws.service.BalancesService;
import com.anpilog.budget.ws.shared.dto.BalanceDTO;
import com.anpilog.budget.ws.ui.model.response.ErrorMessages;

public class BalancesServiceImpl implements BalancesService {

	DAO database;

	public BalancesServiceImpl(DAO database) {
		this.database = database;
	}

	@Override
	public List<BalanceDTO> getBalances() {

		List<BalanceDTO> balances = null;

		try {
			this.database.openConnection();
			balances = this.database.getBalances();
		} finally {
			this.database.closeConnection();
		}

		return balances;
	}
	
	@Override
	public BalanceDTO getPendingBalance() {

		BalanceDTO balance = null;

		try {
			this.database.openConnection();
			balance = this.database.getBalances().stream().filter(b -> b.getStatus()==DataRetrievalStatus.PENDING).findFirst().orElse(null);
		} finally {
			this.database.closeConnection();
		}

		return balance;
	}

	@Override
	public BalanceDTO getLatestBalance() {

		BalanceDTO balance = null;

		try {
			this.database.openConnection();
			balance = this.database.getBalances().stream().max(Comparator.comparing(BalanceDTO::getDate))
					.orElseThrow(NoSuchElementException::new);
		} finally {
			this.database.closeConnection();
		}

		return balance;
	}
	
	@Override
	public BalanceDTO getBalance(String id) {
		BalanceDTO returnValue = null;

		try {
			this.database.openConnection();
			returnValue = this.database.getBalance(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		} finally {
			this.database.closeConnection();
		}

		return returnValue;
	}

	@Override
	public BalanceDTO createBalance(BalanceDTO balanceDto) {

		// Record data into a database
		BalanceDTO returnValue = this.saveBalance(balanceDto);

		// Return back the bank
		return returnValue;
	}

	private BalanceDTO saveBalance(BalanceDTO balanceDto) {

		BalanceDTO returnValue = null;

		try {
			this.database.openConnection();
			returnValue = this.database.saveBalance(balanceDto);
		} finally {
			this.database.closeConnection();
		}

		return returnValue;
	}
	
	@Override
	public void updateBalance(BalanceDTO balanceDto) {
		try {
			this.database.openConnection();
			this.database.updateBalance(balanceDto);
		} catch (Exception ex) {
			throw new CouldNotUpdateRecordException(ex.getMessage());
		} finally {
			this.database.closeConnection();
		}
	}

}
