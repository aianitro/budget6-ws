package com.anpilog.budget.ws.ui.entrypoints;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.anpilog.budget.ws.io.entity.ReferenceEntity;
import com.anpilog.budget.ws.io.entity.TransactionEntity;
import com.anpilog.budget.ws.io.entity.enums.DataRetrievalStatus;
import com.anpilog.budget.ws.service.TotalsService;
import com.anpilog.budget.ws.shared.dto.AccountDTO;
import com.anpilog.budget.ws.shared.dto.TotalDTO;
import com.anpilog.budget.ws.shared.dto.TransactionDTO;
import com.anpilog.budget.ws.ui.model.request.CreateTotalRequest;
import com.anpilog.budget.ws.ui.model.request.UpdateTotalRequest;
import com.anpilog.budget.ws.ui.model.response.DeleteTotalResponse;
import com.anpilog.budget.ws.ui.model.response.RequestOperation;
import com.anpilog.budget.ws.ui.model.response.ResponseStatus;
import com.anpilog.budget.ws.ui.model.response.TotalResponse;

@Path("/totals")
public class TotalsEntryPoint {

	@Autowired
	TotalsService totalsService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TotalResponse> getTotals() {

		List<TotalDTO> totals = totalsService.getTotals();

		// Prepare return value
		List<TotalResponse> returnValue = new ArrayList<TotalResponse>();
		for (TotalDTO totalDto : totals) {
			TotalResponse totalResponse = new TotalResponse();
			BeanUtils.copyProperties(totalDto, totalResponse);

			// Account
			ReferenceEntity accountReference = new ReferenceEntity();
			BeanUtils.copyProperties(totalDto.getAccount(), accountReference);
			totalResponse.setAccount(accountReference);

			// Transactions
			if (totalDto.getTransactions().size() > 0) {
				List<TransactionEntity> transactionEntities = new ArrayList<TransactionEntity>();
				for (TransactionDTO transactionDto : totalDto.getTransactions()) {
					TransactionEntity transactionEntity = new TransactionEntity();
					BeanUtils.copyProperties(transactionDto, transactionEntity);
					transactionEntities.add(transactionEntity);
				}
				totalResponse.setTransactions(transactionEntities);
			}

			returnValue.add(totalResponse);
		}

		return returnValue;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public TotalResponse getTotal(@PathParam("id") String id) {

		TotalDTO totalDto = totalsService.getTotal(id);

		// Prepare response
		TotalResponse returnValue = new TotalResponse();
		BeanUtils.copyProperties(totalDto, returnValue);

		// Account
		ReferenceEntity accountReference = new ReferenceEntity();
		BeanUtils.copyProperties(totalDto.getAccount(), accountReference);
		returnValue.setAccount(accountReference);

		// Transactions
		if (totalDto.getTransactions().size() > 0) {
			List<TransactionEntity> transactionEntities = new ArrayList<TransactionEntity>();
			for (TransactionDTO transactionDto : totalDto.getTransactions()) {
				TransactionEntity transactionEntity = new TransactionEntity();
				BeanUtils.copyProperties(transactionDto, transactionEntity);
				transactionEntities.add(transactionEntity);
			}
			returnValue.setTransactions(transactionEntities);
		}

		return returnValue;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TotalResponse createTotal(CreateTotalRequest requestObject) {

		// Prepare DTO
		TotalDTO totalDto = new TotalDTO();
		BeanUtils.copyProperties(requestObject, totalDto);
		totalDto.setStatus(DataRetrievalStatus.COMPLETED);

		// Account
		AccountDTO accountDto = new AccountDTO();
		BeanUtils.copyProperties(requestObject.getAccount(), accountDto);
		totalDto.setAccount(accountDto);

		// Transactions
		if (requestObject.getTransactions() != null && requestObject.getTransactions().size() > 0) {
			List<TransactionDTO> transactionsDto = new ArrayList<TransactionDTO>();
			for (TransactionEntity transactionEntity : requestObject.getTransactions()) {
				TransactionDTO transactionDto = new TransactionDTO();
				BeanUtils.copyProperties(transactionEntity, transactionDto);
				transactionsDto.add(transactionDto);
			}
			totalDto.setTransactions(transactionsDto);
		}

		// Create new total
		TotalDTO createdTotal = totalsService.createTotal(totalDto);

		// Prepare response
		TotalResponse returnValue = new TotalResponse();
		BeanUtils.copyProperties(createdTotal, returnValue);

		// Account
		ReferenceEntity accountReference = new ReferenceEntity();
		BeanUtils.copyProperties(createdTotal.getAccount(), accountReference);
		returnValue.setAccount(accountReference);

		return returnValue;
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TotalResponse updateTotal(@PathParam("id") String id, UpdateTotalRequest updateRequest) {

		TotalDTO totalDto = totalsService.getTotal(id);

		if (updateRequest.getDate() != null)
			totalDto.setDate(updateRequest.getDate());
		if (updateRequest.getAccount() != null) {
			AccountDTO accountDto = new AccountDTO();
			BeanUtils.copyProperties(updateRequest.getAccount(), accountDto);
			totalDto.setAccount(accountDto);
		}
		if (updateRequest.getAmount() != null)
			totalDto.setAmount(updateRequest.getAmount());
		if (updateRequest.getDifference() != null)
			totalDto.setDifference(updateRequest.getDifference());
		if (updateRequest.getTransactions() != null && updateRequest.getTransactions().size() > 0) {
			List<TransactionDTO> transactionsDto = new ArrayList<TransactionDTO>();
			for (TransactionEntity transactionEntity : updateRequest.getTransactions()) {
				TransactionDTO transactionDto = new TransactionDTO();
				BeanUtils.copyProperties(transactionEntity, transactionDto);
				transactionsDto.add(transactionDto);
			}
			totalDto.setTransactions(transactionsDto);
		}

		// Update total details
		totalsService.updateTotal(totalDto);

		// Prepare response
		TotalResponse returnValue = new TotalResponse();
		BeanUtils.copyProperties(totalDto, returnValue);

		// Account
		ReferenceEntity accountReference = new ReferenceEntity();
		BeanUtils.copyProperties(totalDto.getAccount(), accountReference);
		returnValue.setAccount(accountReference);

		// Transactions
		if (totalDto.getTransactions().size() > 0) {
			List<TransactionEntity> transactionsEntity = new ArrayList<TransactionEntity>();
			for (TransactionDTO transactionDto : totalDto.getTransactions()) {
				TransactionEntity transactionEntity = new TransactionEntity();
				BeanUtils.copyProperties(transactionDto, transactionEntity);
				// To avoid StackOverflowException
				transactionEntity.setTotal(null);
				transactionsEntity.add(transactionEntity);
			}
			// returnValue.setTransactions(transactionsEntity);
		}

		return returnValue;
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DeleteTotalResponse deleteTotal(@PathParam("id") String id) {
		DeleteTotalResponse returnValue = new DeleteTotalResponse();
		returnValue.setRequestOperation(RequestOperation.DELETE);

		TotalDTO totalDto = totalsService.getTotal(id);

		totalsService.deleteTotal(totalDto);

		returnValue.setResponseStatus(ResponseStatus.SUCCESS);

		return returnValue;
	}

}
