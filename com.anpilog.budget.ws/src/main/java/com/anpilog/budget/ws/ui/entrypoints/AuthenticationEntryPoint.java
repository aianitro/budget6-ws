package com.anpilog.budget.ws.ui.entrypoints;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.anpilog.budget.ws.service.AuthenticationService;
import com.anpilog.budget.ws.service.impl.AuthenticationServiceImpl;
import com.anpilog.budget.ws.shared.dto.UserDTO;
import com.anpilog.budget.ws.ui.model.request.LoginCredentials;
import com.anpilog.budget.ws.ui.model.response.AuthenticationDetails;

@Path("/authentication")
public class AuthenticationEntryPoint {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public AuthenticationDetails userLogin(LoginCredentials loginCredentials) {

		AuthenticationDetails returnValue = new AuthenticationDetails();
		
		AuthenticationService authenticationService = new AuthenticationServiceImpl();
		UserDTO authenticatedUser = authenticationService.authenticate(loginCredentials.getUsername(), loginCredentials.getPassword());
		
		// Reset access token
		authenticationService.resetSecurityCredentials(loginCredentials.getPassword(), authenticatedUser);
		
		String accessToken = authenticationService.issueAccessToken(authenticatedUser);
		returnValue.setId(authenticatedUser.getUserId());
		returnValue.setToken(accessToken);
		
		return returnValue;
		
	}
}
