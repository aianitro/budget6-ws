package com.anpilog.budget.ws.io.dao;

import java.util.List;

import com.anpilog.budget.ws.shared.dto.AccountDTO;
import com.anpilog.budget.ws.shared.dto.BankDTO;
import com.anpilog.budget.ws.shared.dto.SecretQuestionDTO;
import com.anpilog.budget.ws.shared.dto.UserDTO;

public interface DAO {
	void openConnection();
	void closeConnection();
	
	// Users
	List<UserDTO> getUsers(int start, int limit);
	UserDTO getUser(String id);
    UserDTO getUserByUserName(String userName);    
    UserDTO saveUser(UserDTO userDto);    
    void updateUser(UserDTO userDto);
	UserDTO getUserByEmailToken(String token);
	void deleteUser(UserDTO userDto);
	
	// Accounts
	List<AccountDTO> getAccounts(int start, int limit);
	AccountDTO getAccount(String id);
	AccountDTO getAccountByName(String name);	
	AccountDTO saveAccount(AccountDTO accountDto);
	void updateAccount(AccountDTO accountDto);
    void deleteAccount(AccountDTO accountDto);
	
	// Secret questions
	List<SecretQuestionDTO> getSecretQuestions();
	SecretQuestionDTO getSecretQuestion(String id);
	SecretQuestionDTO saveSecretQuestion(SecretQuestionDTO secretQuestionDto);
	void updateSecretQuestion(SecretQuestionDTO secretQuestionDto);
	void deleteSecretQuestion(SecretQuestionDTO secretQuestionDto);
	
	// Banks
	List<BankDTO> getBanks();
	BankDTO getBank(String id);
	BankDTO getBankByName(String name);	
	BankDTO saveBank(BankDTO bankDto);
	void updateBank(BankDTO bankDto);
	void deleteBank(BankDTO bankDto);
	
}
