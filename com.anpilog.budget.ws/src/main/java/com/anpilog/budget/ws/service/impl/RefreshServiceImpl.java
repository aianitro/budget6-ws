package com.anpilog.budget.ws.service.impl;

import com.anpilog.budget.ws.io.dao.DAO;
import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;
import com.anpilog.budget.ws.service.BalancesService;
import com.anpilog.budget.ws.service.RefreshService;
import com.anpilog.budget.ws.shared.dto.BalanceDTO;
import com.anpilog.budget.ws.shared.dto.RefreshStatusDTO;
import com.anpilog.budget.ws.ui.model.request.RefreshRequest;

public class RefreshServiceImpl implements RefreshService {

	DAO database;

	public RefreshServiceImpl(DAO database) {
		this.database = database;
	}

	@Override
	public RefreshStatusDTO getStatus() {

		RefreshStatusDTO returnValue = new RefreshStatusDTO();

		// Find latest balance in PENDING state (must be 1 or 0)
		BalancesService balancesService = new BalancesServiceImpl(database);
		BalanceDTO latestBalanceDto = balancesService.getLatestBalance();

		returnValue.setStatus(latestBalanceDto.getStatus());

		if (latestBalanceDto.getStatus() == DataRetrievalStatus.COMPLETED) {
			returnValue.setDetails("Date: " + latestBalanceDto.getDate().toString());

		} else {
			// Count how many totals are in PENDING
			long pendingTotalsCount = latestBalanceDto.getTotals().stream().filter(t -> t.getStatus()==DataRetrievalStatus.PENDING).count();			
			returnValue.setDetails("Accounts to refresh: " + pendingTotalsCount);
		}

		return returnValue;
	}

	@Override
	public RefreshStatusDTO refreshAccounts(RefreshRequest requestObject) {

		boolean runBankAccounts = requestObject.getRunBankAccounts();
		//String newTotalsResult = dataHandler.getNewTotals(runBankAccounts);

		// TODO
		// boolean runCreditScore = requestObject.getRunCreditScore();

		RefreshStatusDTO returnValue = new RefreshStatusDTO();
		returnValue.setStatus(DataRetrievalStatus.PENDING);
		//returnValue.setDetails("Banks are " + (requestObject.getRunBankAccounts() ? "ON" : "OFF") + ", credit score is "
		//		+ (requestObject.getRunCreditScore() ? "ON" : "OFF") + "\\r\\n Accounts: \\r\\n" + newTotalsResult);

		return returnValue;
	}

}
