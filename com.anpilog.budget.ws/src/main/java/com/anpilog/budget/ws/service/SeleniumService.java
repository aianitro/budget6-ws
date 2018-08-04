package com.anpilog.budget.ws.service;

import java.util.List;

import com.anpilog.budget.ws.exceptions.ConfigurationException;
import com.anpilog.budget.ws.shared.dto.AccountDTO;
import com.anpilog.budget.ws.shared.dto.TotalDTO;
import com.anpilog.budget.ws.shared.dto.TransactionDTO;
import com.anpilog.budget.ws.ui.model.pages.AccountPage;

public interface SeleniumService {
		
	AccountPage getAccountPage(AccountDTO accountDto) throws ConfigurationException;
	void findTransactionsForDifference(TotalDTO totalDto, AccountPage accountPage,
			List<TransactionDTO> prevTransactions);
	void refreshTotals(List<TotalDTO> totalsToRefresh, List<TransactionDTO> prevTransactions) throws ConfigurationException;
	boolean isOnline();

}
