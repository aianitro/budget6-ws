package com.anpilog.budget.ws.io.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;

import com.anpilog.budget.ws.io.dao.DAO;
import com.anpilog.budget.ws.io.entity.AccountEntity;
import com.anpilog.budget.ws.io.entity.BalanceEntity;
import com.anpilog.budget.ws.io.entity.BankEntity;
import com.anpilog.budget.ws.io.entity.SecretQuestionEntity;
import com.anpilog.budget.ws.io.entity.TotalEntity;
import com.anpilog.budget.ws.io.entity.TransactionEntity;
import com.anpilog.budget.ws.io.entity.UserEntity;
import com.anpilog.budget.ws.shared.dto.AccountDTO;
import com.anpilog.budget.ws.shared.dto.BalanceDTO;
import com.anpilog.budget.ws.shared.dto.BankDTO;
import com.anpilog.budget.ws.shared.dto.SecretQuestionDTO;
import com.anpilog.budget.ws.shared.dto.TotalDTO;
import com.anpilog.budget.ws.shared.dto.TransactionDTO;
import com.anpilog.budget.ws.shared.dto.UserDTO;
import com.anpilog.budget.ws.utils.HibernateUtils;

public class MySQLDAO implements DAO {

	Session session;

	@Override
	public void openConnection() {
		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		session = sessionFactory.openSession();
	}

	@Override
	public void closeConnection() {
		if (session != null)
			session.close();
	}

	// USERS

	@Override
	public List<UserDTO> getUsers(int start, int limit) {

		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against a particular persistent class
		CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);

		// Query roots always reference entities
		Root<UserEntity> root = cq.from(UserEntity.class);
		cq.select(root);

		// Fetch results from start to a number of "limit"
		List<UserEntity> searchResults = session.createQuery(cq).setFirstResult(start).setMaxResults(limit)
				.getResultList();

		List<UserDTO> returnValue = new ArrayList<UserDTO>();
		for (UserEntity userEntity : searchResults) {
			UserDTO userDto = new UserDTO();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}

		return returnValue;
	}

	@Override
	public UserDTO getUser(String id) {
		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against a particular persistent class
		CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);

		// Query roots always reference entitie
		Root<UserEntity> root = cq.from(UserEntity.class);
		cq.select(root);
		cq.where(cb.equal(root.get("userId"), id));

		// Fetch single result
		UserEntity userEntity = session.createQuery(cq).getSingleResult();

		UserDTO returnValue = new UserDTO();
		BeanUtils.copyProperties(userEntity, returnValue);

		return returnValue;
	}

	@Override
	public UserDTO getUserByUserName(String userName) {

		UserDTO returnValue = null;

		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against a particular persistent class
		CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);

		// Query roots always reference entitie
		Root<UserEntity> root = cq.from(UserEntity.class);
		cq.select(root);
		cq.where(cb.equal(root.get("email"), userName));

		// Fetch single result
		Query<UserEntity> query = session.createQuery(cq);
		List<UserEntity> resultList = query.getResultList();
		if (resultList != null && resultList.size() > 0) {
			UserEntity userEntity = resultList.get(0);
			returnValue = new UserDTO();
			BeanUtils.copyProperties(userEntity, returnValue);
		}

		return returnValue;
	}

	@Override
	public UserDTO saveUser(UserDTO user) {
		UserDTO returnValue = null;
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);

		session.beginTransaction();
		session.save(userEntity);
		session.getTransaction().commit();

		returnValue = new UserDTO();
		BeanUtils.copyProperties(userEntity, returnValue);

		return returnValue;
	}

	@Override
	public void updateUser(UserDTO userProfile) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userProfile, userEntity);

		session.beginTransaction();
		session.update(userEntity);
		session.getTransaction().commit();

	}

	@Override
	public void deleteUser(UserDTO userPofile) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userPofile, userEntity);

		session.beginTransaction();
		session.delete(userEntity);
		session.getTransaction().commit();
	}

	@Override
	public UserDTO getUserByEmailToken(String token) {
		UserDTO returnValue = null;

		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against a particular persistent class
		CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);

		// Query roots always reference entitie
		Root<UserEntity> root = cq.from(UserEntity.class);
		cq.select(root);
		cq.where(cb.equal(root.get("emailVerificationToken"), token));

		// Fetch single result
		Query<UserEntity> query = session.createQuery(cq);
		List<UserEntity> resultList = query.getResultList();
		if (resultList != null && resultList.size() > 0) {
			UserEntity userEntity = resultList.get(0);
			returnValue = new UserDTO();
			BeanUtils.copyProperties(userEntity, returnValue);
		}

		return returnValue;
	}

	// BANKS

	@Override
	public List<BankDTO> getBanks() {
		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against particular persistent class
		CriteriaQuery<BankEntity> cq = cb.createQuery(BankEntity.class);

		// Query
		Root<BankEntity> root = cq.from(BankEntity.class);
		cq.select(root);

		// Fetch single result
		List<BankEntity> searchResults = session.createQuery(cq).getResultList();

		List<BankDTO> returnValue = new ArrayList<BankDTO>();
		for (BankEntity bankEntity : searchResults) {
			BankDTO bankDto = new BankDTO();
			BeanUtils.copyProperties(bankEntity, bankDto);

			// Secret questions
			if (bankEntity.getSecretQuestions().size() > 0) {
				Set<SecretQuestionDTO> secretQuestionsDto = new HashSet<SecretQuestionDTO>();
				for (SecretQuestionEntity secretQuestionEntity : bankEntity.getSecretQuestions()) {
					SecretQuestionDTO secretQuestionDto = new SecretQuestionDTO();
					BeanUtils.copyProperties(secretQuestionEntity, secretQuestionDto);
					secretQuestionsDto.add(secretQuestionDto);
				}
				bankDto.setSecretQuestions(secretQuestionsDto);
			}

			returnValue.add(bankDto);
		}

		return returnValue;
	}

	@Override
	public BankDTO getBank(String id) {
		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against particular persistent class
		CriteriaQuery<BankEntity> cq = cb.createQuery(BankEntity.class);

		// Query
		Root<BankEntity> root = cq.from(BankEntity.class);
		cq.select(root);
		cq.where(cb.equal(root.get("id"), id));

		// Fetch single result
		BankEntity bankEntity = session.createQuery(cq).getSingleResult();

		BankDTO returnValue = new BankDTO();
		BeanUtils.copyProperties(bankEntity, returnValue);

		// Secret questions
		if (bankEntity.getSecretQuestions().size() > 0) {
			Set<SecretQuestionDTO> secretQuestionsDto = new HashSet<SecretQuestionDTO>();
			for (SecretQuestionEntity secretQuestionEntity : bankEntity.getSecretQuestions()) {
				SecretQuestionDTO secretQuestionDto = new SecretQuestionDTO();
				BeanUtils.copyProperties(secretQuestionEntity, secretQuestionDto);
				secretQuestionsDto.add(secretQuestionDto);
			}
			returnValue.setSecretQuestions(secretQuestionsDto);
		}

		return returnValue;
	}

	@Override
	public BankDTO getBankByName(String name) {

		BankDTO returnValue = null;

		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against particular persistent class
		CriteriaQuery<BankEntity> cq = cb.createQuery(BankEntity.class);

		// Query
		Root<BankEntity> root = cq.from(BankEntity.class);
		cq.select(root);
		cq.where(cb.equal(root.get("name"), name));

		// Fetch single result
		Query<BankEntity> query = session.createQuery(cq);
		List<BankEntity> resultList = query.getResultList();
		if (resultList != null && resultList.size() > 0) {
			BankEntity bankEntity = resultList.get(0);
			returnValue = new BankDTO();
			BeanUtils.copyProperties(bankEntity, returnValue);
		}

		return returnValue;
	}

	@Override
	public BankDTO saveBank(BankDTO bankDto) {
		BankDTO returnValue = new BankDTO();
		BankEntity bankEntity = new BankEntity();
		BeanUtils.copyProperties(bankDto, bankEntity);

		if (bankEntity.getSecretQuestions() != null)
			for (SecretQuestionEntity secretQuestionEntity : bankEntity.getSecretQuestions())
				secretQuestionEntity.setBank(bankEntity);

		session.beginTransaction();
		session.save(bankEntity);
		session.getTransaction().commit();

		BeanUtils.copyProperties(bankEntity, returnValue);

		// Secret questions
		if (bankEntity.getSecretQuestions() != null && bankEntity.getSecretQuestions().size() > 0) {
			Set<SecretQuestionDTO> secretQuestionsDto = new HashSet<SecretQuestionDTO>();
			for (SecretQuestionEntity secretQuestionEntity : bankEntity.getSecretQuestions()) {
				SecretQuestionDTO secretQuestionDto = new SecretQuestionDTO();
				BeanUtils.copyProperties(secretQuestionEntity, secretQuestionDto);
				secretQuestionsDto.add(secretQuestionDto);
			}
			returnValue.setSecretQuestions(secretQuestionsDto);
		}

		return returnValue;
	}

	@Override
	public void updateBank(BankDTO bankDto) {
		BankEntity bankEntity = new BankEntity();
		BeanUtils.copyProperties(bankDto, bankEntity);

		// Secret questions
		if (bankDto.getSecretQuestions() != null && bankDto.getSecretQuestions().size() > 0) {
			Set<SecretQuestionEntity> secretQuestionsEntities = new HashSet<SecretQuestionEntity>();
			for (SecretQuestionDTO secretQuestionDto : bankDto.getSecretQuestions()) {
				SecretQuestionEntity secretQuestionsEntity = new SecretQuestionEntity();
				BeanUtils.copyProperties(secretQuestionDto, secretQuestionsEntity);
				secretQuestionsEntity.setBank(bankEntity);
				secretQuestionsEntities.add(secretQuestionsEntity);
			}
			bankEntity.setSecretQuestions(secretQuestionsEntities);
		}

		session.beginTransaction();
		session.update(bankEntity);
		session.getTransaction().commit();

		// Secret questions
		if (bankEntity.getSecretQuestions() != null && bankEntity.getSecretQuestions().size() > 0) {
			Set<SecretQuestionDTO> secretQuestionsDto = new HashSet<SecretQuestionDTO>();
			for (SecretQuestionEntity secretQuestionEntity : bankEntity.getSecretQuestions()) {
				SecretQuestionDTO secretQuestionDto = new SecretQuestionDTO();
				BeanUtils.copyProperties(secretQuestionEntity, secretQuestionDto);
				secretQuestionsDto.add(secretQuestionDto);
			}
			bankDto.setSecretQuestions(secretQuestionsDto);
		}

	}

	@Override
	public void deleteBank(BankDTO bankDto) {
		BankEntity bankEntity = new BankEntity();
		BeanUtils.copyProperties(bankDto, bankEntity);

		// Secret questions
		if (bankDto.getSecretQuestions() != null && bankDto.getSecretQuestions().size() > 0) {
			Set<SecretQuestionEntity> secretQuestionsEntities = new HashSet<SecretQuestionEntity>();
			for (SecretQuestionDTO secretQuestionDto : bankDto.getSecretQuestions()) {
				SecretQuestionEntity secretQuestionsEntity = new SecretQuestionEntity();
				BeanUtils.copyProperties(secretQuestionDto, secretQuestionsEntity);
				secretQuestionsEntity.setBank(bankEntity);
				secretQuestionsEntities.add(secretQuestionsEntity);
			}
			bankEntity.setSecretQuestions(secretQuestionsEntities);
		}

		session.beginTransaction();
		session.delete(bankEntity);
		session.getTransaction().commit();

	}

	// ACCOUNTS

	@Override
	public List<AccountDTO> getAccounts(int start, int limit) {
		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against particular persistent class
		CriteriaQuery<AccountEntity> cq = cb.createQuery(AccountEntity.class);

		// Query
		Root<AccountEntity> root = cq.from(AccountEntity.class);
		cq.select(root).orderBy(cb.asc(root.get("id")));

		// Fetch single result
		List<AccountEntity> searchResults = session.createQuery(cq).setFirstResult(start).setMaxResults(limit)
				.getResultList();

		List<AccountDTO> returnValue = new ArrayList<AccountDTO>();
		for (AccountEntity accountEntity : searchResults) {
			AccountDTO accountDto = new AccountDTO();
			BeanUtils.copyProperties(accountEntity, accountDto);
			returnValue.add(accountDto);
		}

		return returnValue;
	}

	@Override
	public List<AccountDTO> getAccountsEnabledOnly(int start, int limit) {
		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against particular persistent class
		CriteriaQuery<AccountEntity> cq = cb.createQuery(AccountEntity.class);

		// Query
		Root<AccountEntity> root = cq.from(AccountEntity.class);
		cq.select(root);
		cq.where(cb.equal(root.get("isEnabled"), true));

		// Fetch single result
		List<AccountEntity> searchResults = session.createQuery(cq).setFirstResult(start).setMaxResults(limit)
				.getResultList();

		List<AccountDTO> returnValue = new ArrayList<AccountDTO>();
		for (AccountEntity accountEntity : searchResults) {
			AccountDTO accountDto = new AccountDTO();
			BeanUtils.copyProperties(accountEntity, accountDto);
			returnValue.add(accountDto);
		}

		return returnValue;
	}

	@Override
	public AccountDTO getAccount(String id) {
		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against particular persistent class
		CriteriaQuery<AccountEntity> cq = cb.createQuery(AccountEntity.class);

		// Query
		Root<AccountEntity> root = cq.from(AccountEntity.class);
		cq.select(root);
		cq.where(cb.equal(root.get("id"), id));

		// Fetch single result
		AccountEntity accountEntity = session.createQuery(cq).getSingleResult();

		AccountDTO returnValue = new AccountDTO();
		BeanUtils.copyProperties(accountEntity, returnValue);

		return returnValue;
	}

	@Override
	public AccountDTO getAccountByName(String name) {

		AccountDTO returnValue = null;

		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against particular persistent class
		CriteriaQuery<AccountEntity> cq = cb.createQuery(AccountEntity.class);

		// Query
		Root<AccountEntity> root = cq.from(AccountEntity.class);
		cq.select(root);
		cq.where(cb.equal(root.get("name"), name));

		// Fetch single result
		Query<AccountEntity> query = session.createQuery(cq);
		List<AccountEntity> resultList = query.getResultList();
		if (resultList != null && resultList.size() > 0) {
			AccountEntity accountEntity = resultList.get(0);
			returnValue = new AccountDTO();
			BeanUtils.copyProperties(accountEntity, returnValue);
		}

		return returnValue;
	}

	@Override
	public AccountDTO saveAccount(AccountDTO accountDto) {
		AccountDTO returnValue = new AccountDTO();
		AccountEntity accountEntity = new AccountEntity();
		BeanUtils.copyProperties(accountDto, accountEntity);

		session.beginTransaction();
		session.save(accountEntity);
		session.getTransaction().commit();

		BeanUtils.copyProperties(accountEntity, returnValue);

		return returnValue;
	}

	@Override
	public void updateAccount(AccountDTO accountDto) {
		AccountEntity accountEntity = new AccountEntity();
		BeanUtils.copyProperties(accountDto, accountEntity);

		session.beginTransaction();
		session.update(accountEntity);
		session.getTransaction().commit();

	}

	@Override
	public void deleteAccount(AccountDTO accountDto) {
		AccountEntity accountEntity = new AccountEntity();
		BeanUtils.copyProperties(accountDto, accountEntity);

		session.beginTransaction();
		session.delete(accountEntity);
		session.getTransaction().commit();

	}

	// SECRET QUESTIONS

	@Override
	public List<SecretQuestionDTO> getSecretQuestions() {
		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against particular persistent class
		CriteriaQuery<SecretQuestionEntity> cq = cb.createQuery(SecretQuestionEntity.class);

		// Query
		Root<SecretQuestionEntity> root = cq.from(SecretQuestionEntity.class);
		cq.select(root);

		// Fetch single result
		List<SecretQuestionEntity> searchResults = session.createQuery(cq).getResultList();

		List<SecretQuestionDTO> returnValue = new ArrayList<SecretQuestionDTO>();
		for (SecretQuestionEntity secretQuestionEntity : searchResults) {
			SecretQuestionDTO secretQuestionDto = new SecretQuestionDTO();
			BeanUtils.copyProperties(secretQuestionEntity, secretQuestionDto);

			returnValue.add(secretQuestionDto);
		}

		return returnValue;
	}

	// BALANCES

	@Override
	public List<BalanceDTO> getBalances() {
		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against particular persistent class
		CriteriaQuery<BalanceEntity> cq = cb.createQuery(BalanceEntity.class);

		// Query
		Root<BalanceEntity> root = cq.from(BalanceEntity.class);
		cq.select(root);

		// Fetch single result
		List<BalanceEntity> searchResults = session.createQuery(cq).getResultList();

		List<BalanceDTO> returnValue = new ArrayList<BalanceDTO>();
		for (BalanceEntity balanceEntity : searchResults) {
			BalanceDTO balanceDto = new BalanceDTO();
			BeanUtils.copyProperties(balanceEntity, balanceDto);

			// Totals
			if (balanceEntity.getTotals().size() > 0) {
				List<TotalDTO> totalsDto = new ArrayList<TotalDTO>();
				for (TotalEntity totalEntity : balanceEntity.getTotals()) {
					TotalDTO totalDto = new TotalDTO();
					BeanUtils.copyProperties(totalEntity, totalDto);

					// Account
					AccountDTO accountDTO = new AccountDTO();
					BeanUtils.copyProperties(totalEntity.getAccount(), accountDTO);
					totalDto.setAccount(accountDTO);

					// Transactions
					if (totalEntity.getTransactions().size() > 0) {
						List<TransactionDTO> transactionsDto = new ArrayList<TransactionDTO>();
						for (TransactionEntity transactionEntity : totalEntity.getTransactions()) {
							TransactionDTO transactionDto = new TransactionDTO();
							BeanUtils.copyProperties(transactionEntity, transactionDto);
							transactionsDto.add(transactionDto);
						}
						totalDto.setTransactions(transactionsDto);
					}

					totalsDto.add(totalDto);
				}
				balanceDto.setTotals(totalsDto);
			}

			returnValue.add(balanceDto);
		}

		return returnValue;
	}

	@Override
	public BalanceDTO getBalance(String id) {
		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against particular persistent class
		CriteriaQuery<BalanceEntity> cq = cb.createQuery(BalanceEntity.class);

		// Query
		Root<BalanceEntity> root = cq.from(BalanceEntity.class);
		cq.select(root);
		cq.where(cb.equal(root.get("id"), id));

		// Fetch single result
		BalanceEntity balanceEntity = session.createQuery(cq).getSingleResult();

		BalanceDTO returnValue = new BalanceDTO();
		BeanUtils.copyProperties(balanceEntity, returnValue);

		// Totals
		if (balanceEntity.getTotals().size() > 0) {
			List<TotalDTO> totalsDto = new ArrayList<TotalDTO>();
			for (TotalEntity totalEntity : balanceEntity.getTotals()) {
				TotalDTO totalDto = new TotalDTO();
				BeanUtils.copyProperties(totalEntity, totalDto);

				// Account
				AccountDTO accountDTO = new AccountDTO();
				BeanUtils.copyProperties(totalEntity.getAccount(), accountDTO);
				totalDto.setAccount(accountDTO);

				// Transactions
				if (totalEntity.getTransactions().size() > 0) {
					List<TransactionDTO> transactionsDto = new ArrayList<TransactionDTO>();
					for (TransactionEntity transactionEntity : totalEntity.getTransactions()) {
						TransactionDTO transactionDto = new TransactionDTO();
						BeanUtils.copyProperties(transactionEntity, transactionDto);
						transactionsDto.add(transactionDto);
					}
					totalDto.setTransactions(transactionsDto);
				}

				totalsDto.add(totalDto);
			}
			returnValue.setTotals(totalsDto);
		}

		return returnValue;
	}

	@Override
	public BalanceDTO saveBalance(BalanceDTO balanceDto) {

		BalanceEntity balanceEntity = new BalanceEntity();
		BeanUtils.copyProperties(balanceDto, balanceEntity);

		// Totals
		List<TotalEntity> totalsEntity = new ArrayList<TotalEntity>();
		for (TotalDTO totalDto : balanceDto.getTotals()) {

			TotalEntity totalEntity = new TotalEntity();
			BeanUtils.copyProperties(totalDto, totalEntity);

			// Account
			AccountEntity accountEntity = new AccountEntity();
			BeanUtils.copyProperties(totalDto.getAccount(), accountEntity);
			totalEntity.setAccount(accountEntity);

			totalEntity.setBalance(balanceEntity);
			totalsEntity.add(totalEntity);
		}
		balanceEntity.setTotals(totalsEntity);

		session.beginTransaction();
		session.save(balanceEntity);
		session.getTransaction().commit();

		BalanceDTO returnValue = new BalanceDTO();
		BeanUtils.copyProperties(balanceEntity, returnValue);

		// Transactions
		if (balanceEntity.getTotals() != null && balanceEntity.getTotals().size() > 0) {
			List<TotalDTO> totalsDto = new ArrayList<TotalDTO>();
			for (TotalEntity totalEntity : balanceEntity.getTotals()) {
				TotalDTO totalDto = new TotalDTO();
				BeanUtils.copyProperties(totalEntity, totalDto);

				AccountDTO accountDTO = new AccountDTO();
				BeanUtils.copyProperties(totalEntity.getAccount(), accountDTO);
				totalDto.setAccount(accountDTO);

				totalsDto.add(totalDto);
			}
			returnValue.setTotals(totalsDto);
		}

		return returnValue;
	}

	@Override
	public void updateBalance(BalanceDTO balanceDto) {

		BalanceEntity balanceEntity = new BalanceEntity();
		BeanUtils.copyProperties(balanceDto, balanceEntity);

		// Totals
		List<TotalEntity> totalsEntity = new ArrayList<TotalEntity>();
		for (TotalDTO totalDto : balanceDto.getTotals()) {

			TotalEntity totalEntity = new TotalEntity();
			BeanUtils.copyProperties(totalDto, totalEntity);

			if (totalDto.getTransactions() != null && totalDto.getTransactions().size() > 0) {
				List<TransactionEntity> transactionsEntity = new ArrayList<TransactionEntity>();
				for (TransactionDTO transactionDto : totalDto.getTransactions()) {
					TransactionEntity transactionEntity = new TransactionEntity();
					BeanUtils.copyProperties(transactionDto, transactionEntity);
					transactionEntity.setTotal(totalEntity);
					transactionsEntity.add(transactionEntity);
				}
				totalEntity.setTransactions(transactionsEntity);
			}

			// Account
			AccountEntity accountEntity = new AccountEntity();
			BeanUtils.copyProperties(totalDto.getAccount(), accountEntity);
			totalEntity.setAccount(accountEntity);

			totalEntity.setBalance(balanceEntity);
			totalsEntity.add(totalEntity);
		}
		balanceEntity.setTotals(totalsEntity);

		session.beginTransaction();
		session.update(balanceEntity);
		session.getTransaction().commit();
	}

	// TOTALS

	@Override
	public List<TotalDTO> getTotals() {
		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against particular persistent class
		CriteriaQuery<TotalEntity> cq = cb.createQuery(TotalEntity.class);

		// Query
		Root<TotalEntity> root = cq.from(TotalEntity.class);
		cq.orderBy(cb.desc(root.get("date")), cb.desc(root.get("id")));
		cq.select(root);

		// Fetch single result
		List<TotalEntity> searchResults = session.createQuery(cq).getResultList();

		List<TotalDTO> returnValue = new ArrayList<TotalDTO>();
		for (TotalEntity totalEntity : searchResults) {
			TotalDTO totalDto = new TotalDTO();
			BeanUtils.copyProperties(totalEntity, totalDto);

			// Account
			AccountDTO accountDto = new AccountDTO();
			BeanUtils.copyProperties(totalEntity.getAccount(), accountDto);
			totalDto.setAccount(accountDto);

			// Transactions
			if (totalEntity.getTransactions().size() > 0) {
				List<TransactionDTO> transactionsDto = new ArrayList<TransactionDTO>();
				for (TransactionEntity transactionEntity : totalEntity.getTransactions()) {
					TransactionDTO transactionDto = new TransactionDTO();
					BeanUtils.copyProperties(transactionEntity, transactionDto);
					transactionsDto.add(transactionDto);
				}
				totalDto.setTransactions(transactionsDto);
			}

			returnValue.add(totalDto);
		}

		return returnValue;
	}
	
	@Override
	public List<TotalDTO> getLastTotals() {
		
		// Query
		String sql = "SELECT\n" + 
				"  all_totals.id,\n" + 
				"  date,\n" + 
				"  amount,\n" + 
				"  previous_amount,\n" + 				
				"  difference,\n" + 
				"  all_totals.account_id,\n" + 
				"  all_totals.balance_id,\n" +
				"  all_totals.status\n" +
				"FROM b6db.totals all_totals\n" +
				"INNER JOIN (SELECT\n" + 
				"  id\n" + 
				"FROM b6db.accounts\n"+
				"WHERE isEnabled=TRUE) active_accounts\n" + 
				"  ON all_totals.account_id = active_accounts.id\n" +				
				"INNER JOIN (SELECT\n" + 
				"  MAX(date) AS max_date,\n" + 
				"  account_id\n" + 
				"FROM b6db.totals\n" +
				"GROUP BY account_id) last_totals\n" + 
				"  ON all_totals.account_id = last_totals.account_id\n" + 
				"  AND all_totals.date = last_totals.max_date";
		Query<TotalEntity> query = session.createNativeQuery(sql, TotalEntity.class);
		List<TotalEntity> searchResults = (List<TotalEntity>) query.getResultList();			

		List<TotalDTO> returnValue = new ArrayList<TotalDTO>();
		for (TotalEntity totalEntity : searchResults) {
			TotalDTO totalDto = new TotalDTO();
			BeanUtils.copyProperties(totalEntity, totalDto);
			
			// Account
			AccountDTO accountDto = new AccountDTO();
			BeanUtils.copyProperties(totalEntity.getAccount(), accountDto);
			totalDto.setAccount(accountDto);

			// Transactions
			if (totalEntity.getTransactions().size() > 0) {
				List<TransactionDTO> transactionsDto = new ArrayList<TransactionDTO>();
				for (TransactionEntity transactionEntity : totalEntity.getTransactions()) {
					TransactionDTO transactionDto = new TransactionDTO();
					BeanUtils.copyProperties(transactionEntity, transactionDto);
					transactionsDto.add(transactionDto);
				}
				totalDto.setTransactions(transactionsDto);
			}

			returnValue.add(totalDto);
		}

		return returnValue;
	}

	@Override
	public TotalDTO getTotal(String id) {
		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against particular persistent class
		CriteriaQuery<TotalEntity> cq = cb.createQuery(TotalEntity.class);

		// Query
		Root<TotalEntity> root = cq.from(TotalEntity.class);
		cq.select(root);
		cq.where(cb.equal(root.get("id"), id));

		// Fetch single result
		TotalEntity totalEntity = session.createQuery(cq).getSingleResult();

		TotalDTO returnValue = new TotalDTO();
		BeanUtils.copyProperties(totalEntity, returnValue);

		// Account
		AccountDTO accountDto = new AccountDTO();
		BeanUtils.copyProperties(totalEntity.getAccount(), accountDto);
		returnValue.setAccount(accountDto);

		// Transactions
		if (totalEntity.getTransactions().size() > 0) {
			List<TransactionDTO> transactionsDto = new ArrayList<TransactionDTO>();
			for (TransactionEntity transactionEntity : totalEntity.getTransactions()) {
				TransactionDTO transactionDto = new TransactionDTO();
				BeanUtils.copyProperties(transactionEntity, transactionDto);
				transactionDto.setTotal(returnValue);
				transactionsDto.add(transactionDto);
			}
			returnValue.setTransactions(transactionsDto);
		}

		return returnValue;
	}

	@Override
	public TotalDTO saveTotal(TotalDTO totalDto) {

		TotalEntity totalEntity = new TotalEntity();
		BeanUtils.copyProperties(totalDto, totalEntity);

		// Account
		AccountEntity accountEntity = new AccountEntity();
		BeanUtils.copyProperties(totalDto.getAccount(), accountEntity);
		totalEntity.setAccount(accountEntity);

		// Transactions
		List<TransactionEntity> transactionEntities = new ArrayList<TransactionEntity>();
		if (totalEntity.getTransactions() != null)
			for (TransactionDTO transactionDto : totalDto.getTransactions()) {
				TransactionEntity transactionEntity = new TransactionEntity();
				BeanUtils.copyProperties(transactionDto, transactionEntity);
				transactionEntity.setTotal(totalEntity);
				transactionEntities.add(transactionEntity);
			}
		totalEntity.setTransactions(transactionEntities);

		session.beginTransaction();
		session.save(totalEntity);
		session.getTransaction().commit();

		TotalDTO returnValue = new TotalDTO();
		BeanUtils.copyProperties(totalEntity, returnValue);

		// Account
		AccountDTO accountDto = new AccountDTO();
		BeanUtils.copyProperties(totalEntity.getAccount(), accountDto);
		returnValue.setAccount(accountDto);

		// Transactions
		if (totalEntity.getTransactions() != null && totalEntity.getTransactions().size() > 0) {
			List<TransactionDTO> transactionsDto = new ArrayList<TransactionDTO>();
			for (TransactionEntity transactionEntity : totalEntity.getTransactions()) {
				TransactionDTO transactionDto = new TransactionDTO();
				BeanUtils.copyProperties(transactionEntity, transactionDto);
				transactionsDto.add(transactionDto);
			}
			returnValue.setTransactions(transactionsDto);
		}

		return returnValue;
	}

	@Override
	public void updateTotal(TotalDTO totalDto) {
		TotalEntity totalEntity = new TotalEntity();
		BeanUtils.copyProperties(totalDto, totalEntity);

		// Account
		AccountEntity accountEntity = new AccountEntity();
		BeanUtils.copyProperties(totalDto.getAccount(), accountEntity);
		totalEntity.setAccount(accountEntity);

		// Transactions
		if (totalDto.getTransactions() != null && totalDto.getTransactions().size() > 0) {
			List<TransactionEntity> transactionsEntity = new ArrayList<TransactionEntity>();
			for (TransactionDTO transactionDto : totalDto.getTransactions()) {
				TransactionEntity transactionEntity = new TransactionEntity();
				BeanUtils.copyProperties(transactionDto, transactionEntity);
				transactionEntity.setTotal(totalEntity);
				transactionsEntity.add(transactionEntity);
			}
			totalEntity.setTransactions(transactionsEntity);
		}

		session.beginTransaction();
		session.update(totalEntity);
		session.getTransaction().commit();

	}

	@Override
	public void deleteTotal(TotalDTO totalDto) {
		TotalEntity totalEntity = new TotalEntity();
		BeanUtils.copyProperties(totalDto, totalEntity);

		// Transactions
		if (totalDto.getTransactions() != null && totalDto.getTransactions().size() > 0) {
			List<TransactionEntity> transactionEntities = new ArrayList<TransactionEntity>();
			for (TransactionDTO transactionDto : totalDto.getTransactions()) {
				TransactionEntity transactionEntity = new TransactionEntity();
				BeanUtils.copyProperties(transactionDto, transactionEntity);
				transactionEntity.setTotal(totalEntity);
				transactionEntities.add(transactionEntity);
			}
			totalEntity.setTransactions(transactionEntities);
		}

		session.beginTransaction();
		session.delete(totalEntity);
		session.getTransaction().commit();

	}

	// TOTALS

	@Override
	public List<TransactionDTO> getTransactions() {
		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against particular persistent class
		CriteriaQuery<TransactionEntity> cq = cb.createQuery(TransactionEntity.class);

		// Query
		Root<TransactionEntity> root = cq.from(TransactionEntity.class);
		cq.select(root);

		// Fetch single result
		List<TransactionEntity> searchResults = session.createQuery(cq).getResultList();

		List<TransactionDTO> returnValue = new ArrayList<TransactionDTO>();
		for (TransactionEntity transactionEntity : searchResults) {
			if (!transactionEntity.getTotal().getAccount().getIsEnabled())
				continue;

			TransactionDTO transactionDto = new TransactionDTO();
			BeanUtils.copyProperties(transactionEntity, transactionDto);

			// Total
			TotalDTO totalDto = new TotalDTO();
			BeanUtils.copyProperties(transactionEntity.getTotal(), totalDto);
			transactionDto.setTotal(totalDto);

			// Account
			AccountDTO accountDto = new AccountDTO();
			BeanUtils.copyProperties(transactionEntity.getTotal().getAccount(), accountDto);
			totalDto.setAccount(accountDto);

			returnValue.add(transactionDto);
		}

		return returnValue;
	}

}
