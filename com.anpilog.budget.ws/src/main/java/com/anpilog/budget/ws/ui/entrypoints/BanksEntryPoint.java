package com.anpilog.budget.ws.ui.entrypoints;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.anpilog.budget.ws.io.entity.SecretQuestionEntity;
import com.anpilog.budget.ws.service.BanksService;
import com.anpilog.budget.ws.shared.dto.BankDTO;
import com.anpilog.budget.ws.shared.dto.SecretQuestionDTO;
import com.anpilog.budget.ws.ui.model.request.CreateBankRequest;
import com.anpilog.budget.ws.ui.model.request.UpdateBankRequest;
import com.anpilog.budget.ws.ui.model.response.BankResponse;
import com.anpilog.budget.ws.ui.model.response.DeleteBankResponse;
import com.anpilog.budget.ws.ui.model.response.RequestOperation;
import com.anpilog.budget.ws.ui.model.response.ResponseStatus;

@Path("/banks")
public class BanksEntryPoint {

	@Autowired
	BanksService banksService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BankResponse> getBanks() {

		List<BankDTO> banks = banksService.getBanks();

		// Prepare return value
		List<BankResponse> returnValue = new ArrayList<BankResponse>();
		for (BankDTO bankDto : banks) {
			BankResponse bankResponse = new BankResponse();
			BeanUtils.copyProperties(bankDto, bankResponse);
			returnValue.add(bankResponse);
		}

		return returnValue;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public BankResponse getBank(@PathParam("id") String id) {
		BankResponse returnValue = null;

		BankDTO bankDto = banksService.getBank(id);

		// Prepare response
		returnValue = new BankResponse();
		BeanUtils.copyProperties(bankDto, returnValue);

		return returnValue;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BankResponse createBank(CreateBankRequest requestObject) {

		BankResponse returnValue = new BankResponse();

		// Prepare DTO
		BankDTO bankDto = new BankDTO();
		BeanUtils.copyProperties(requestObject, bankDto);

		// Create new bank
		BankDTO createdBank = banksService.createBank(bankDto);

		// Prepare response
		BeanUtils.copyProperties(createdBank, returnValue);

		return returnValue;
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BankResponse updateBank(@PathParam("id") String id, UpdateBankRequest updateBankRequest) {

		BankDTO bankDto = banksService.getBank(id);

		// Set only those fields you would like to be updated with this request
		if (updateBankRequest.getName() != null && !updateBankRequest.getName().isEmpty())
			bankDto.setName(updateBankRequest.getName());

		if (updateBankRequest.getIsEnabled() != null)
			bankDto.setIsEnabled(updateBankRequest.getIsEnabled());

		if (updateBankRequest.getSecretQuestions() != null) {
			Set<SecretQuestionDTO> secretQuestionsDto = new HashSet<SecretQuestionDTO>();
			for (SecretQuestionEntity secretQuestionEntity : updateBankRequest.getSecretQuestions()) {
				SecretQuestionDTO secretQuestionDto = new SecretQuestionDTO();
				BeanUtils.copyProperties(secretQuestionEntity, secretQuestionDto);
				secretQuestionsDto.add(secretQuestionDto);
			}
			bankDto.setSecretQuestions(secretQuestionsDto);
		}

		// Update account details
		banksService.updateBank(bankDto);

		// Prepare response
		BankResponse returnValue = new BankResponse();
		BeanUtils.copyProperties(bankDto, returnValue);

		return returnValue;
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DeleteBankResponse deleteBank(@PathParam("id") String id) {
		DeleteBankResponse returnValue = new DeleteBankResponse();
		returnValue.setRequestOperation(RequestOperation.DELETE);

		BankDTO bankDto = banksService.getBank(id);

		banksService.deleteBank(bankDto);

		returnValue.setResponseStatus(ResponseStatus.SUCCESS);

		return returnValue;
	}

}
