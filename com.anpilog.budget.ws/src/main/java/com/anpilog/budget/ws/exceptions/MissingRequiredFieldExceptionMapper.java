package com.anpilog.budget.ws.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.anpilog.budget.ws.ui.model.response.ErrorMessage;
import com.anpilog.budget.ws.ui.model.response.ErrorMessages;

@Provider
public class MissingRequiredFieldExceptionMapper implements ExceptionMapper<MissingRequiredFieldException> {

	@Override
	public Response toResponse(MissingRequiredFieldException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),
				ErrorMessages.MISSING_REQUIRED_FIELD.name());

		return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
	}

}
