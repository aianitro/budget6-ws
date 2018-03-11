package com.anpilog.budget.ws.service;

import com.anpilog.budget.ws.shared.dto.RefreshStatusDTO;

public interface RefreshService {
		
	RefreshStatusDTO getRefreshStatus();
	RefreshStatusDTO refreshAccounts();

}
