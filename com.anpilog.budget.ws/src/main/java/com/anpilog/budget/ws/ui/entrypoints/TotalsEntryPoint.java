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

import com.anpilog.budget.ws.service.TotalsService;
import com.anpilog.budget.ws.shared.dto.TotalDTO;
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
			returnValue.add(totalResponse);
		}

		return returnValue;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public TotalResponse getTotal(@PathParam("id") String id) {
		TotalResponse returnValue = null;

		TotalDTO totalDto = totalsService.getTotal(id);

		// Prepare response
		returnValue = new TotalResponse();
		BeanUtils.copyProperties(totalDto, returnValue);

		return returnValue;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TotalResponse createTotal(CreateTotalRequest requestObject) {

		TotalResponse returnValue = new TotalResponse();

		// Prepare DTO
		TotalDTO totalDto = new TotalDTO();
		BeanUtils.copyProperties(requestObject, totalDto);

		// Create new total
		TotalDTO createdTotal = totalsService.createTotal(totalDto);		

		// Prepare response
		BeanUtils.copyProperties(createdTotal, returnValue);

		return returnValue;
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TotalResponse updateTotal(@PathParam("id") String id, UpdateTotalRequest updateRequest) {

		TotalDTO totalDto = totalsService.getTotal(id);
		
		// Update total details
		totalsService.updateTotal(totalDto);

		// Prepare response
		TotalResponse returnValue = new TotalResponse();
		BeanUtils.copyProperties(totalDto, returnValue);

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
