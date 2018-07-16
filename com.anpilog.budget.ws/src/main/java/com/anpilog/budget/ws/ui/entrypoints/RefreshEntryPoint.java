package com.anpilog.budget.ws.ui.entrypoints;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.anpilog.budget.ws.service.RefreshService;
import com.anpilog.budget.ws.shared.dto.RefreshStatusDTO;
import com.anpilog.budget.ws.ui.model.request.RefreshRequest;
import com.anpilog.budget.ws.ui.model.response.RefreshResponse;

@Path("/refresh")
public class RefreshEntryPoint {

	@Autowired
	RefreshService refreshService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public RefreshResponse getRefreshStatus() {
				
		RefreshResponse refreshResponse = new RefreshResponse();
		RefreshStatusDTO refreshStatusDTO = refreshService.getRefreshStatus();
		BeanUtils.copyProperties(refreshStatusDTO, refreshResponse);

		return refreshResponse;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RefreshResponse refreshAccounts(RefreshRequest requestObject) {

		RefreshResponse refreshResponse = new RefreshResponse();
		RefreshStatusDTO refreshStatusDTO = refreshService.refreshAccounts(requestObject);
		BeanUtils.copyProperties(refreshStatusDTO, refreshResponse);

		return refreshResponse;
	}

}