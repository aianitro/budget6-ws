package com.anpilog.budget.ws.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.anpilog.budget.ws.ui.model.response.ErrorMessage;
import com.anpilog.budget.ws.ui.model.response.ErrorMessages;

@Provider
public class NoRecordFoundExceptionMapper implements ExceptionMapper<NoRecordFoundException> {

	@Override
	public Response toResponse(NoRecordFoundException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),
				ErrorMessages.NO_RECORD_FOUND.name());

		return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
	}
}