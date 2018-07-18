package com.anpilog.budget.ws.ui.entrypoints;

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

import com.anpilog.budget.ws.service.BalancesService;
import com.anpilog.budget.ws.shared.dto.BalanceDTO;
import com.anpilog.budget.ws.ui.model.request.CreateBalanceRequest;
import com.anpilog.budget.ws.ui.model.response.BalanceResponse;

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
			
			returnValue.add(balanceResponse);
		}

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
		//returnValue.getTotals().stream().forEach(t -> t.setBalance(null));

		return returnValue;
	}

}
