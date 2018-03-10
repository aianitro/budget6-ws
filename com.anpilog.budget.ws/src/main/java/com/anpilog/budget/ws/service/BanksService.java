package com.anpilog.budget.ws.service;

import java.util.List;

import com.anpilog.budget.ws.shared.dto.BankDTO;

public interface BanksService {
		
	List<BankDTO> getBanks();
	BankDTO getBank(String id);
	BankDTO getBankByName(String name);
	BankDTO createBank(BankDTO bankDto);
	void updateBank(BankDTO bankDto);
	void deleteBank(BankDTO bankDto);
	
}
