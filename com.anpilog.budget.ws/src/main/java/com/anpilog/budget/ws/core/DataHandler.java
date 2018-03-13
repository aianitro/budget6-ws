package com.anpilog.budget.ws.core;

import java.util.List;

import com.anpilog.budget.ws.io.dao.DAO;
import com.anpilog.budget.ws.io.dao.impl.MySQLDAO;
import com.anpilog.budget.ws.service.AccountsService;
import com.anpilog.budget.ws.service.TotalsService;
import com.anpilog.budget.ws.service.impl.AccountsServiceImpl;
import com.anpilog.budget.ws.service.impl.TotalsServiceImpl;
import com.anpilog.budget.ws.shared.dto.TotalDTO;

public class DataHandler {

	DAO database;
	AccountsService accountsService;
	TotalsService totalsService = new TotalsServiceImpl(null);

	public String getNewTotals() {
		this.database = new MySQLDAO();
		accountsService = new AccountsServiceImpl(database);
		totalsService = new TotalsServiceImpl(database);
		
		// Get latest totals
		List<TotalDTO> latestTotals = totalsService.getLastTotals();
		
		// Getting list of accounts need update
		//List<AccountEntity> accountsToUpdate = accountsService.
		
		//
		StringBuilder sb = new StringBuilder();
		latestTotals.stream().forEach(t -> sb.append(t.getId()).append("\\r\\n"));
		
		return sb.toString();
	}

}
