package com.anpilog.budget.ws.ui.entrypoints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.anpilog.budget.ws.io.entity.ReferenceEntity;
import com.anpilog.budget.ws.io.entity.TransactionEntity;
import com.anpilog.budget.ws.service.BalancesService;
import com.anpilog.budget.ws.shared.dto.BalanceDTO;
import com.anpilog.budget.ws.shared.dto.TotalDTO;
import com.anpilog.budget.ws.shared.dto.TransactionDTO;
import com.anpilog.budget.ws.ui.model.request.CreateBalanceRequest;
import com.anpilog.budget.ws.ui.model.response.BalanceResponse;
import com.anpilog.budget.ws.ui.model.response.TotalResponse;

@Path("/balances")
public class BalancesEntryPoint {

	@Autowired
	BalancesService balancesService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BalanceResponse> getBalances() {

		List<BalanceDTO> balances = balancesService.getBalances();

		// Prepare return value
		List<BalanceResponse> returnValue = new ArrayList<BalanceResponse>();
		for (BalanceDTO balanceDto : balances) {
			BalanceResponse balanceResponse = new BalanceResponse();
			BeanUtils.copyProperties(balanceDto, balanceResponse);

			List<TotalResponse> totals = new ArrayList<TotalResponse>();
			for (TotalDTO totalDTO : balanceDto.getTotals()) {
				TotalResponse total = new TotalResponse();
				BeanUtils.copyProperties(totalDTO, total);

				// Account
				ReferenceEntity accountEntity = new ReferenceEntity();
				BeanUtils.copyProperties(totalDTO.getAccount(), accountEntity);
				total.setAccount(accountEntity);

				// Transactions
				if (totalDTO.getTransactions().size() > 0) {
					List<TransactionEntity> transactionEntities = new ArrayList<TransactionEntity>();
					for (TransactionDTO transactionDto : totalDTO.getTransactions()) {
						TransactionEntity transactionEntity = new TransactionEntity();
						BeanUtils.copyProperties(transactionDto, transactionEntity);
						transactionEntities.add(transactionEntity);
					}
					total.setTransactions(transactionEntities);
				}

				totals.add(total);
			}
			Collections.sort(totals);
			balanceResponse.setTotals(totals);

			returnValue.add(balanceResponse);
		}

		return returnValue;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public BalanceResponse getBalance(@PathParam("id") String id) {

		BalanceDTO balanceDTO = balancesService.getBalance(id);

		// Prepare return value
		BalanceResponse returnValue = new BalanceResponse();
		BeanUtils.copyProperties(balanceDTO, returnValue);

		List<TotalResponse> totals = new ArrayList<TotalResponse>();
		for (TotalDTO totalDTO : balanceDTO.getTotals()) {
			TotalResponse total = new TotalResponse();
			BeanUtils.copyProperties(totalDTO, total);

			// Account
			ReferenceEntity accountEntity = new ReferenceEntity();
			BeanUtils.copyProperties(totalDTO.getAccount(), accountEntity);
			total.setAccount(accountEntity);

			// Transactions
			if (totalDTO.getTransactions().size() > 0) {
				List<TransactionEntity> transactionEntities = new ArrayList<TransactionEntity>();
				for (TransactionDTO transactionDto : totalDTO.getTransactions()) {
					TransactionEntity transactionEntity = new TransactionEntity();
					BeanUtils.copyProperties(transactionDto, transactionEntity);
					transactionEntities.add(transactionEntity);
				}
				total.setTransactions(transactionEntities);
			}

			totals.add(total);
		}
		Collections.sort(totals);
		returnValue.setTotals(totals);

		return returnValue;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BalanceResponse createBalance(CreateBalanceRequest requestObject) {

		// Prepare DTO
		BalanceDTO balanceDto = new BalanceDTO();
		BeanUtils.copyProperties(requestObject, balanceDto);

		// Create new balance
		BalanceDTO createdBalance = balancesService.createBalance(balanceDto);

		// Prepare response
		BalanceResponse returnValue = new BalanceResponse();
		BeanUtils.copyProperties(createdBalance, returnValue);

		// To avoid StackOverflowException
		// returnValue.getTotals().stream().forEach(t -> t.setBalance(null));

		return returnValue;
	}

}
