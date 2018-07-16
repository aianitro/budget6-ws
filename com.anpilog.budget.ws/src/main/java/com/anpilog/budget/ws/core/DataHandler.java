package com.anpilog.budget.ws.core;

import java.util.ArrayList;
import java.util.List;

import com.anpilog.budget.ws.io.dao.DAO;
import com.anpilog.budget.ws.io.dao.impl.MySQLDAO;
import com.anpilog.budget.ws.service.AccountsService;
import com.anpilog.budget.ws.service.TotalsService;
import com.anpilog.budget.ws.service.TransactionsService;
import com.anpilog.budget.ws.service.impl.AccountsServiceImpl;
import com.anpilog.budget.ws.service.impl.TotalsServiceImpl;
import com.anpilog.budget.ws.service.impl.TransactionsServiceImpl;
import com.anpilog.budget.ws.shared.dto.AccountDTO;
import com.anpilog.budget.ws.shared.dto.TotalDTO;
import com.anpilog.budget.ws.shared.dto.TransactionDTO;
import com.anpilog.budget.ws.utils.DateUtils;

public class DataHandler {

	DAO database;
	AccountsService accountsService;
	TotalsService totalsService = new TotalsServiceImpl(null);
	TransactionsService transactionsService = new TransactionsServiceImpl(null);

	public String getNewTotals() {

		// Getting only totals need be updated:
		// - not today's
		// - have at least initial total entered
		List<TotalDTO> totalsToUpdate = getTotalsToUpdate();
		
		// Getting all transactions to exclude 
		// while finding for new totals
		List<TransactionDTO> allTransactions = getAllTransactions();
		
		// temporary indicating response output
		StringBuilder sb = new StringBuilder();
		totalsToUpdate.stream().forEach(t -> sb.append(t.getAccount().getName()).append("(").append(t.getDate()).append("), "));
		sb.append("Total transactions = ").append(allTransactions.size());

		return sb.toString();
	}

	private List<TotalDTO> getTotalsToUpdate() {
		this.database = new MySQLDAO();
		accountsService = new AccountsServiceImpl(database);
		totalsService = new TotalsServiceImpl(database);

		// Get all enabled accounts
		List<AccountDTO> accounts = accountsService.getAccountsEnabledOnly(0, 100);

		// Get latest totals
		List<TotalDTO> latestTotals = totalsService.getLastTotals();

		List<TotalDTO> result = new ArrayList<TotalDTO>();

		latestTotals.stream().filter(t -> !DateUtils.isDateToday(t.getDate()) && t.getAccount().getBank() != null)
				.forEach(t -> {
					if ((accounts.stream().filter(a -> a.getId() == t.getAccount().getId()).findFirst()
							.orElse(null)) != null)
						result.add(t);
				});

		return result;
	}
	
	private List<TransactionDTO> getAllTransactions() {
		this.database = new MySQLDAO();
		transactionsService = new TransactionsServiceImpl(database);

		return transactionsService.getTransactions();
	}

}
