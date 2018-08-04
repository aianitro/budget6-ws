package com.anpilog.budget.ws.ui.entrypoints;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.anpilog.budget.ws.exceptions.ConfigurationException;
import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;
import com.anpilog.budget.ws.service.BalancesService;
import com.anpilog.budget.ws.service.RefreshService;
import com.anpilog.budget.ws.service.TotalsService;
import com.anpilog.budget.ws.service.TransactionsService;
import com.anpilog.budget.ws.service.impl.SeleniumServiceImpl;
import com.anpilog.budget.ws.shared.dto.BalanceDTO;
import com.anpilog.budget.ws.shared.dto.RefreshStatusDTO;
import com.anpilog.budget.ws.shared.dto.TotalDTO;
import com.anpilog.budget.ws.shared.dto.TransactionDTO;
import com.anpilog.budget.ws.ui.model.request.RefreshRequest;
import com.anpilog.budget.ws.ui.model.response.RefreshResponse;

@Path("/refresh")
public class RefreshEntryPoint {

	@Autowired
	RefreshService refreshService;
	@Autowired
	BalancesService balancesService;
	@Autowired
	TotalsService totalsService;
	@Autowired
	TransactionsService transactionsService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public RefreshResponse getRefreshStatus() {

		RefreshResponse refreshResponse = new RefreshResponse();
		RefreshStatusDTO refreshStatusDTO = refreshService.getStatus();
		BeanUtils.copyProperties(refreshStatusDTO, refreshResponse);

		return refreshResponse;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RefreshResponse refreshAccounts(RefreshRequest requestObject) throws ConfigurationException {

		// Check how many accounts need refresh - quit fast if 0
		List<TotalDTO> totalsToUpdate = totalsService.getOutdatedTotals();

		// Create balance
		BalanceDTO balanceDto = new BalanceDTO();
		balanceDto.setDate(LocalDate.now());
		balanceDto.setStatus(DataRetrievalStatus.PENDING);

		// Create totals
		List<TotalDTO> newTotals = new ArrayList<TotalDTO>();
		for (TotalDTO totalDto : totalsToUpdate) {
			TotalDTO newTotalDto = new TotalDTO();
			newTotalDto.setAccount(totalDto.getAccount());
			newTotalDto.setPreviousAmount(totalDto.getAmount());
			newTotalDto.setDate(LocalDate.now());
			newTotalDto.setStatus(DataRetrievalStatus.PENDING);
			newTotals.add(newTotalDto);
		}
		balanceDto.setTotals(newTotals);

		// Save balance
		BalanceDTO createdBalance = balancesService.createBalance(balanceDto);
		
		// Getting all transactions to exclude
		// while finding for new totals
		List<TransactionDTO> allTransactions = transactionsService.getTransactions();
		
		// Start fetching new data with Selenium
		SeleniumServiceImpl seleniumService = new SeleniumServiceImpl();
		seleniumService.refreshTotals(createdBalance.getTotals(), allTransactions);
		
		// If all totals successfully refreshed changing status for Balance
		boolean isRefreshSuccessful = true;
		for(TotalDTO totalDTO: balanceDto.getTotals())
			if(totalDTO.getStatus()!=DataRetrievalStatus.COMPLETED) {			
				isRefreshSuccessful = false;
				break;
			}
		if(isRefreshSuccessful)
			balanceDto.setStatus(DataRetrievalStatus.COMPLETED);
		
		balancesService.updateBalance(createdBalance);			

		// Status
		RefreshResponse refreshResponse = new RefreshResponse();
		RefreshStatusDTO refreshStatusDTO = refreshService.getStatus();
		BeanUtils.copyProperties(refreshStatusDTO, refreshResponse);

		return refreshResponse;
	}

}
