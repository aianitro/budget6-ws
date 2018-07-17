package com.anpilog.budget.ws.ui.entrypoints;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.anpilog.budget.ws.io.entity.ReferenceEntity;
import com.anpilog.budget.ws.service.AccountsService;
import com.anpilog.budget.ws.shared.dto.AccountDTO;
import com.anpilog.budget.ws.ui.model.request.CreateAccountRequest;
import com.anpilog.budget.ws.ui.model.request.UpdateAccountRequest;
import com.anpilog.budget.ws.ui.model.response.AccountResponse;
import com.anpilog.budget.ws.ui.model.response.DeleteAccountResponse;
import com.anpilog.budget.ws.ui.model.response.RequestOperation;
import com.anpilog.budget.ws.ui.model.response.ResponseStatus;

@Path("/accounts")
public class AccountsEntryPoint {

	@Autowired
	AccountsService accountService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<AccountResponse> getAccounts(@DefaultValue("0") @QueryParam("start") int start,
			@DefaultValue("10") @QueryParam("limit") int limit) {

		List<AccountDTO> accounts = accountService.getAccounts(start, limit);

		// Prepare return value
		List<AccountResponse> returnValue = new ArrayList<AccountResponse>();
		for (AccountDTO accountDTO : accounts) {
			AccountResponse accountResponse = new AccountResponse();
			BeanUtils.copyProperties(accountDTO, accountResponse);
			ReferenceEntity bankReference = new ReferenceEntity();
			BeanUtils.copyProperties(accountDTO.getBank(), bankReference);
			accountResponse.setBank(bankReference);
			returnValue.add(accountResponse);
		}

		return returnValue;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public AccountResponse getAccount(@PathParam("id") String id) {
		AccountResponse returnValue = null;

		AccountDTO accountDto = accountService.getAccount(id);

		// Prepare response
		returnValue = new AccountResponse();
		BeanUtils.copyProperties(accountDto, returnValue);
		ReferenceEntity bankReference = new ReferenceEntity();
		BeanUtils.copyProperties(accountDto.getBank(), bankReference);
		returnValue.setBank(bankReference);

		return returnValue;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AccountResponse createAccount(CreateAccountRequest requestObject) {

		AccountResponse returnValue = new AccountResponse();

		// Prepare DTO
		AccountDTO accountDto = new AccountDTO();
		BeanUtils.copyProperties(requestObject, accountDto);

		// Create new account
		AccountDTO createdAccount = accountService.createAccount(accountDto);

		// Prepare response
		BeanUtils.copyProperties(createdAccount, returnValue);
		ReferenceEntity bankReference = new ReferenceEntity();

		// Set bank reference in response
		if (createdAccount.getBank() != null) {
			BeanUtils.copyProperties(createdAccount.getBank(), bankReference);
			returnValue.setBank(bankReference);
		}

		return returnValue;
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AccountResponse updateAccount(@PathParam("id") String id, UpdateAccountRequest accountDetails) {

		AccountDTO storedAccount = accountService.getAccount(id);

		// Set only those fields you would like to be updated with this request
		if (accountDetails.getName() != null && !accountDetails.getName().isEmpty())
			storedAccount.setName(accountDetails.getName());

		if (accountDetails.getBank() != null)
			storedAccount.setBank(accountDetails.getBank());

		if (accountDetails.getMyPortfolioId() != null && !accountDetails.getMyPortfolioId().isEmpty())
			storedAccount.setMyPortfolioId(accountDetails.getMyPortfolioId());

		if (accountDetails.getIsEnabled() != null)
			storedAccount.setIsEnabled(accountDetails.getIsEnabled());

		if (accountDetails.getIsAutomated() != null)
			storedAccount.setIsAutomated(accountDetails.getIsAutomated());

		// Update account details
		accountService.updateAccountDetails(storedAccount);

		// Prepare response
		AccountResponse returnValue = new AccountResponse();
		BeanUtils.copyProperties(storedAccount, returnValue);
		ReferenceEntity bankReference = new ReferenceEntity();
		BeanUtils.copyProperties(storedAccount.getBank(), bankReference);
		returnValue.setBank(bankReference);

		return returnValue;
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DeleteAccountResponse deleteAccount(@PathParam("id") String id) {
		DeleteAccountResponse returnValue = new DeleteAccountResponse();
		returnValue.setRequestOperation(RequestOperation.DELETE);

		AccountDTO storedAccount = accountService.getAccount(id);

		accountService.deleteAccount(storedAccount);

		returnValue.setResponseStatus(ResponseStatus.SUCCESS);

		return returnValue;
	}

}
