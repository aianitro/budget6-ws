package com.anpilog.budget.ws.ui.entrypoints;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
import com.anpilog.budget.ws.utils.BalanceUtil;
import com.anpilog.budget.ws.utils.DateUtils;

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

		BalanceDTO createdBalance;

		// Get pending balance to re-utilize (if possible)
		BalanceDTO pendingBalance = balancesService.getPendingBalance();

		if (pendingBalance == null) { // All balances are COMPLETED

			// Create new balance
			BalanceDTO balanceDto = new BalanceDTO();
			balanceDto.setDate(LocalDate.now());
			balanceDto.setStatus(DataRetrievalStatus.PENDING);
			balanceDto.setTotals(totalsService.prepareNewTotals());
			createdBalance = balancesService.createBalance(balanceDto);

		} else if (DateUtils.isDateToday(pendingBalance.getDate())) // today's balance simply re-using

			createdBalance = pendingBalance;

		else { // Handling old (not today's) balance) which left in PENDING state

			// 1. Setting old balance to COMPLETE status
			pendingBalance.setStatus(DataRetrievalStatus.COMPLETED);
			for (TotalDTO totalDTO : pendingBalance.getTotals()) {
				if (totalDTO.getStatus() != DataRetrievalStatus.PENDING)
					continue;
				totalDTO.setAmount(totalDTO.getPreviousAmount());
				totalDTO.setDifference(0.0);
				totalDTO.setStatus(DataRetrievalStatus.COMPLETED);
			}
			balancesService.updateBalance(pendingBalance);

			// 2. Creating new balance
			BalanceDTO balanceDto = new BalanceDTO();
			balanceDto.setDate(LocalDate.now());
			balanceDto.setStatus(DataRetrievalStatus.PENDING);
			balanceDto.setTotals(totalsService.prepareNewTotals());
			createdBalance = balancesService.createBalance(balanceDto);
		}

		// Getting all transactions to exclude
		// while finding for new totals
		List<TransactionDTO> allTransactions = transactionsService.getTransactions();

		// Start fetching new data with Selenium
		SeleniumServiceImpl seleniumService = new SeleniumServiceImpl();
		
		// Refresh only not 'COMPLETED' totals
		seleniumService.refreshTotals(createdBalance.getTotals().stream()
				.filter(t -> !t.getStatus().equals(DataRetrievalStatus.COMPLETED)).collect(Collectors.toList()),
				allTransactions);

		// If all totals successfully refreshed changing status for Balance
		boolean isRefreshSuccessful = true;
		for (TotalDTO totalDTO : createdBalance.getTotals())
			if (totalDTO.getStatus() != DataRetrievalStatus.COMPLETED) {
				isRefreshSuccessful = false;
				break;
			}
		if (isRefreshSuccessful)
			createdBalance.setStatus(DataRetrievalStatus.COMPLETED);

		createdBalance.setAmount(BalanceUtil.getBalanceAmount(createdBalance));

		balancesService.updateBalance(createdBalance);

		// Status
		RefreshResponse refreshResponse = new RefreshResponse();
		RefreshStatusDTO refreshStatusDTO = refreshService.getStatus();
		BeanUtils.copyProperties(refreshStatusDTO, refreshResponse);

		return refreshResponse;
	}

}
