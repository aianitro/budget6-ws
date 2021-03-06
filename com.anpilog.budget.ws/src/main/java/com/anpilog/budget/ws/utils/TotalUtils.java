package com.anpilog.budget.ws.utils;

import com.anpilog.budget.ws.exceptions.MissingRequiredFieldException;
import com.anpilog.budget.ws.shared.dto.AccountDTO;
import com.anpilog.budget.ws.ui.model.response.ErrorMessages;

public class TotalUtils {

	public void validateRequiredFields(AccountDTO accountDto) throws MissingRequiredFieldException {

		if (accountDto.getName() == null || accountDto.getName().isEmpty()
				|| (accountDto.getIsAutomated() && accountDto.getBank() == null))
			throw new MissingRequiredFieldException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

	}

}
