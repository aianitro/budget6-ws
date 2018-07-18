package com.anpilog.budget.ws.service.impl;

import com.anpilog.budget.ws.core.DataHandler;
import com.anpilog.budget.ws.io.dao.DAO;
import com.anpilog.budget.ws.io.entity.BalanceEntity;
import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;
import com.anpilog.budget.ws.service.RefreshService;
import com.anpilog.budget.ws.shared.dto.RefreshStatusDTO;
import com.anpilog.budget.ws.ui.model.request.RefreshRequest;

public class RefreshServiceImpl implements RefreshService {

	DAO database;	

	public RefreshServiceImpl(DAO database) {
		this.database = database;
	}

	@Override
	public RefreshStatusDTO getRefreshStatus() {

		RefreshStatusDTO returnValue = new RefreshStatusDTO();
		returnValue.setStatus(DataRetrievalStatus.PENDING);
		returnValue.setDetails("Still working...");

		return returnValue;
	}

	@Override
	public RefreshStatusDTO refreshAccounts(RefreshRequest requestObject) {	
		
		// Creating new Balance entity with 'Pending' status
		BalanceEntity newBalance = new BalanceEntity();
		
		DataHandler dataHandler = new DataHandler();
		
		boolean runBankAccounts = requestObject.getRunBankAccounts();
		String newTotalsResult = dataHandler.getNewTotals(runBankAccounts);
		
		// TODO
		//boolean runCreditScore = requestObject.getRunCreditScore();
		
		RefreshStatusDTO returnValue = new RefreshStatusDTO();
		returnValue.setStatus(DataRetrievalStatus.PENDING);
		returnValue.setDetails("Banks are " + (requestObject.getRunBankAccounts() ? "ON" : "OFF")
				+ ", credit score is " + (requestObject.getRunCreditScore() ? "ON" : "OFF") + "\\r\\n Accounts: \\r\\n" + newTotalsResult);				

		return returnValue;
	}

}
