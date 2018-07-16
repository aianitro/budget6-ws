package com.anpilog.budget.ws.service.impl;

import com.anpilog.budget.ws.core.DataHandler;
import com.anpilog.budget.ws.io.dao.DAO;
import com.anpilog.budget.ws.service.RefreshService;
import com.anpilog.budget.ws.shared.dto.RefreshStatusDTO;
import com.anpilog.budget.ws.ui.model.request.RefreshRequest;
import com.anpilog.budget.ws.ui.model.response.RefreshStatuses;

public class RefreshServiceImpl implements RefreshService {

	DAO database;

	public RefreshServiceImpl(DAO database) {
		this.database = database;
	}

	@Override
	public RefreshStatusDTO getRefreshStatus() {

		RefreshStatusDTO returnValue = new RefreshStatusDTO();
		returnValue.setStatus(RefreshStatuses.PENDING);
		returnValue.setDetails("Still working...");

		return returnValue;
	}

	@Override
	public RefreshStatusDTO refreshAccounts(RefreshRequest requestObject) {		
		DataHandler dataHandler = new DataHandler();
		
		String newTotalsResult = dataHandler.getNewTotals();
		
		RefreshStatusDTO returnValue = new RefreshStatusDTO();
		returnValue.setStatus(RefreshStatuses.STARTED);
		returnValue.setDetails("Banks are " + (requestObject.getRunBankAccounts() ? "ON" : "OFF")
				+ ", credit score is " + (requestObject.getRunCreditScore() ? "ON" : "OFF") + "\\r\\n Accounts: \\r\\n" + newTotalsResult);				

		return returnValue;
	}

}