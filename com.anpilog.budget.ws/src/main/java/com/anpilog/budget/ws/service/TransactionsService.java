package com.anpilog.budget.ws.service;

import java.util.List;

import com.anpilog.budget.ws.shared.dto.TransactionDTO;

public interface TransactionsService {
		
	List<TransactionDTO> getTransactions();
	
}
