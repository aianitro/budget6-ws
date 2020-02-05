package com.anpilog.budget.ws.ui.entrypoints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.anpilog.budget.ws.io.entity.TransactionEntity;
import com.anpilog.budget.ws.service.BalancesService;
import com.anpilog.budget.ws.service.TotalsService;
import com.anpilog.budget.ws.shared.dto.BalanceDTO;
import com.anpilog.budget.ws.shared.dto.TotalDTO;
import com.anpilog.budget.ws.shared.dto.TransactionDTO;
import com.anpilog.budget.ws.ui.model.reference.EntityReference;
import com.anpilog.budget.ws.ui.model.reference.TotalReference;
import com.anpilog.budget.ws.ui.model.request.CreateBalanceRequest;
import com.anpilog.budget.ws.ui.model.response.BalanceResponse;
import com.anpilog.budget.ws.ui.model.response.DeleteBalanceResponse;
import com.anpilog.budget.ws.ui.model.response.RequestOperation;
import com.anpilog.budget.ws.ui.model.response.ResponseStatus;

@Path("/balances")
public class BalancesEntryPoint {

	@Autowired
	BalancesService balancesService;
	@Autowired
	TotalsService totalService;	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BalanceResponse> getBalances() {

		List<BalanceDTO> balances = balancesService.getBalances();

		// Prepare return value
		List<BalanceResponse> returnValue = new ArrayList<BalanceResponse>();
		for (BalanceDTO balanceDto : balances) {
			BalanceResponse balanceResponse = new BalanceResponse();
			BeanUtils.copyProperties(balanceDto, balanceResponse);

			List<TotalReference> totals = new ArrayList<TotalReference>();
			for (TotalDTO totalDTO : balanceDto.getTotals()) {
				TotalReference total = new TotalReference();
				BeanUtils.copyProperties(totalDTO, total);

				// Account
				EntityReference accountEntity = new EntityReference();
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
					total.setTransactions(transactionEntities.size());
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

		List<TotalReference> totalReferences = new ArrayList<TotalReference>();
		for (TotalDTO totalDto : balanceDTO.getTotals()) {
			TotalReference totalReference = new TotalReference();
			BeanUtils.copyProperties(totalDto, totalReference);
			totalReference.setTransactions(totalDto.getTransactions().size());

			// Account
			EntityReference accountEntity = new EntityReference();
			BeanUtils.copyProperties(totalDto.getAccount(), accountEntity);
			totalReference.setAccount(accountEntity);

			totalReferences.add(totalReference);
		}
		Collections.sort(totalReferences);
		returnValue.setTotals(totalReferences);

		return returnValue;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BalanceResponse createBalance(CreateBalanceRequest requestObject) {

		// Prepare DTO
		BalanceDTO balanceDto = new BalanceDTO();
		BeanUtils.copyProperties(requestObject, balanceDto);
		
		// Totals
		if (requestObject.getTotals() != null && requestObject.getTotals().size() > 0) {
			List<TotalDTO> totalsDto = new ArrayList<TotalDTO>();
			for (EntityReference referenceEntity : requestObject.getTotals()) {
				TotalDTO totalDto = totalService.getTotal(referenceEntity.getId().toString());
				totalsDto.add(totalDto);
			}
			balanceDto.setTotals(totalsDto);
		}

		// Create new balance
		BalanceDTO createdBalance = balancesService.createBalance(balanceDto);

		// Prepare response
		BalanceResponse returnValue = new BalanceResponse();
		BeanUtils.copyProperties(createdBalance, returnValue);
		
		// Totals
		if (returnValue.getTotals() != null && returnValue.getTotals().size() > 0) {
			List<TotalReference> totalReferences = new ArrayList<TotalReference>();
			for (TotalDTO totalDto : createdBalance.getTotals()) {
				TotalReference totalReference = new TotalReference();
				BeanUtils.copyProperties(totalDto, totalReference);
				totalReference.setTransactions(totalDto.getTransactions().size());
				
				// Account
				EntityReference accountEntity = new EntityReference();
				BeanUtils.copyProperties(totalDto.getAccount(), accountEntity);
				totalReference.setAccount(accountEntity);
				
				totalReferences.add(totalReference);
			}
			returnValue.setTotals(totalReferences);
		}


		return returnValue;
	}
	

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DeleteBalanceResponse deleteBalance(@PathParam("id") String id) {
		DeleteBalanceResponse returnValue = new DeleteBalanceResponse();
		returnValue.setRequestOperation(RequestOperation.DELETE);

		BalanceDTO balanceDto = balancesService.getBalance(id);

		balancesService.deleteBalance(balanceDto);

		returnValue.setResponseStatus(ResponseStatus.SUCCESS);

		return returnValue;
	}

}
