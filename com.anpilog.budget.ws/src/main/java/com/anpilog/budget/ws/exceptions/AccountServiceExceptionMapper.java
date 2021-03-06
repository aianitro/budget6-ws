package com.anpilog.budget.ws.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.anpilog.budget.ws.ui.model.response.ErrorMessage;
import com.anpilog.budget.ws.ui.model.response.ErrorMessages;

@Provider
public class AccountServiceExceptionMapper implements ExceptionMapper<AccountServiceException> {

	@Override
	public Response toResponse(AccountServiceException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),
				ErrorMessages.RECORD_ALREADY_EXISTS.name());

		return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
	}

}
