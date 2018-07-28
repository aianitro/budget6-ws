package com.anpilog.budget.ws.service;

import com.anpilog.budget.ws.shared.dto.RefreshStatusDTO;
import com.anpilog.budget.ws.ui.model.request.RefreshRequest;

public interface RefreshService {
		
	RefreshStatusDTO getStatus();
	RefreshStatusDTO refreshAccounts(RefreshRequest requestObject);

}
