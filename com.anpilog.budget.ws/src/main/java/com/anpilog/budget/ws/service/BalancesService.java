package com.anpilog.budget.ws.service;

import java.util.List;

import com.anpilog.budget.ws.shared.dto.BalanceDTO;

public interface BalancesService {
		
	List<BalanceDTO> getBalances();
	//List<BalanceDTO> getLastBalances();
	//BalanceDTO getBalance(String id);
	BalanceDTO createBalance(BalanceDTO balanceDto);
	//void updateBalance(BalanceDTO balanceDto);
	//void deleteBalance(BalanceDTO balanceDto);
	
}
