package com.anpilog.budget.ws.service;

import java.util.List;

import com.anpilog.budget.ws.shared.dto.BankDTO;
import com.anpilog.budget.ws.shared.dto.TotalDTO;

public interface TotalsService {
		
	List<TotalDTO> getTotals();
	TotalDTO getTotal(String id);
	TotalDTO createTotal(TotalDTO totalDto);
	void updateTotal(TotalDTO totalDto);
	void deleteTotal(TotalDTO totalDto);
	
}
