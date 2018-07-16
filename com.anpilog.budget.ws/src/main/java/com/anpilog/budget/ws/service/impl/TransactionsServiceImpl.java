package com.anpilog.budget.ws.service.impl;

import java.util.List;

import com.anpilog.budget.ws.io.dao.DAO;
import com.anpilog.budget.ws.service.TransactionsService;
import com.anpilog.budget.ws.shared.dto.TransactionDTO;

public class TransactionsServiceImpl implements TransactionsService {

	DAO database;

	public TransactionsServiceImpl(DAO database) {
		this.database = database;
	}

	@Override
	public List<TransactionDTO> getTransactions() {
		
		List<TransactionDTO> transactions = null;
		
		try {
			this.database.openConnection();
			transactions = this.database.getTransactions();
		} finally {
			this.database.closeConnection();
		}
		
		return transactions;
	}
	
}
