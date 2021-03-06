package com.anpilog.budget.ws.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.anpilog.budget.ws.ui.model.response.ErrorMessage;
import com.anpilog.budget.ws.ui.model.response.ErrorMessages;

@Provider
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException> {

	@Override
	public Response toResponse(AuthenticationException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),
				ErrorMessages.AUTHENTICAION_FAILED.name());

		return Response.status(Response.Status.UNAUTHORIZED).entity(errorMessage).build();
	}
}