package com.anpilog.budget.ws.core;

import java.util.ArrayList;
import java.util.List;

import com.anpilog.budget.ws.io.dao.DAO;
import com.anpilog.budget.ws.io.dao.impl.MySQLDAO;
import com.anpilog.budget.ws.service.AccountsService;
import com.anpilog.budget.ws.service.TotalsService;
import com.anpilog.budget.ws.service.impl.AccountsServiceImpl;
import com.anpilog.budget.ws.service.impl.TotalsServiceImpl;
import com.anpilog.budget.ws.shared.dto.AccountDTO;
import com.anpilog.budget.ws.shared.dto.TotalDTO;
import com.anpilog.budget.ws.utils.DateUtils;

public class DataHandler {

	DAO database;
	AccountsService accountsService;
	TotalsService totalsService = new TotalsServiceImpl(null);

	public String getNewTotals() {

		List<AccountDTO> accountsToUpdate = getAccountsToUpdate();

		StringBuilder sb = new StringBuilder();
		accountsToUpdate.stream().forEach(t -> sb.append(t.getName()).append("\r\n"));

		return sb.toString();
	}

	private List<AccountDTO> getAccountsToUpdate() {
		this.database = new MySQLDAO();
		accountsService = new AccountsServiceImpl(database);
		totalsService = new TotalsServiceImpl(database);

		// Get all enabled accounts
		List<AccountDTO> accounts = accountsService.getAccountsEnabledOnly(0, 100);

		// Get latest totals
		List<TotalDTO> latestTotals = totalsService.getLastTotals();

		List<AccountDTO> result = new ArrayList<AccountDTO>();
		
		latestTotals.stream().filter(t -> !DateUtils.isDateToday(t.getDate()) && t.getAccount().getBank() != null)
				.forEach(t -> {
					if ((accounts.stream().filter(a -> a.getId()==t.getAccount().getId()).findFirst().orElse(null))!=null)
						result.add(t.getAccount());				
				});

		return result;
	}

}
