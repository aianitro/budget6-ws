package com.anpilog.budget.ws.service;

import java.util.List;

import com.anpilog.budget.ws.shared.dto.AccountDTO;

public interface AccountsService {
		
	List<AccountDTO> getAccounts(int start, int limit);
	List<AccountDTO> getAccountsEnabledOnly(int start, int limit);
	AccountDTO getAccount(String id);
	AccountDTO getAccountByName(String name);
	AccountDTO createAccount(AccountDTO accountDto);
	void updateAccountDetails(AccountDTO storedAccount);
	void deleteAccount(AccountDTO storedAccount);
	
}
