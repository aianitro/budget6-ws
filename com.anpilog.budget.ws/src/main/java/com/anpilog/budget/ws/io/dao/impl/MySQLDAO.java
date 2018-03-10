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
import com.anpilog.budget.ws.io.entity.BankEntity;
import com.anpilog.budget.ws.io.entity.SecretQuestionEntity;
import com.anpilog.budget.ws.io.entity.UserEntity;
import com.anpilog.budget.ws.shared.dto.AccountDTO;
import com.anpilog.budget.ws.shared.dto.BankDTO;
import com.anpilog.budget.ws.shared.dto.SecretQuestionDTO;
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

        //Create Criteria against a particular persistent class
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

        //Query roots always reference entities
        Root<UserEntity> root = criteria.from(UserEntity.class);
        criteria.select(root);

        // Fetch results from start to a number of "limit"
        List<UserEntity> searchResults = session.createQuery(criteria).
                setFirstResult(start).
                setMaxResults(limit).
                getResultList();
 
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
		CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

		// Query roots always reference entitie
		Root<UserEntity> root = criteria.from(UserEntity.class);
		criteria.select(root);
		criteria.where(cb.equal(root.get("userId"), id));

		// Fetch single result
		UserEntity userEntity = session.createQuery(criteria).getSingleResult();

		UserDTO returnValue = new UserDTO();
		BeanUtils.copyProperties(userEntity, returnValue);

		return returnValue;
	}
	
	@Override
	public UserDTO getUserByUserName(String userName) {

		UserDTO returnValue = null;

		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against a particular persistent class
		CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

		// Query roots always reference entitie
		Root<UserEntity> root = criteria.from(UserEntity.class);
		criteria.select(root);
		criteria.where(cb.equal(root.get("email"), userName));

		// Fetch single result
		Query<UserEntity> query = session.createQuery(criteria);
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
		CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

		// Query roots always reference entitie
		Root<UserEntity> root = criteria.from(UserEntity.class);
		criteria.select(root);
		criteria.where(cb.equal(root.get("emailVerificationToken"), token));

		// Fetch single result
		Query<UserEntity> query = session.createQuery(criteria);
		List<UserEntity> resultList = query.getResultList();
		if (resultList != null && resultList.size() > 0) {
			UserEntity userEntity = resultList.get(0);
			returnValue = new UserDTO();
			BeanUtils.copyProperties(userEntity, returnValue);
		}

		return returnValue;
	}
	
	
	// ACCOUNTS

	@Override
	public List<AccountDTO> getAccounts(int start, int limit) {
		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against particular persistent class
		CriteriaQuery<AccountEntity> criteria = cb.createQuery(AccountEntity.class);

		// Query
		Root<AccountEntity> root = criteria.from(AccountEntity.class);
		criteria.select(root);

		// Fetch single result
		List<AccountEntity> searchResults = session.createQuery(criteria).setFirstResult(start).setMaxResults(limit)
				.getResultList();

		List<AccountDTO> returnValue = new ArrayList<AccountDTO>();
		for(AccountEntity accountEntity: searchResults) {
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
		CriteriaQuery<AccountEntity> criteria = cb.createQuery(AccountEntity.class);

		// Query
		Root<AccountEntity> root = criteria.from(AccountEntity.class);
		criteria.select(root);
		criteria.where(cb.equal(root.get("id"), id));

		// Fetch single result
		AccountEntity accountEntity = session.createQuery(criteria).getSingleResult();

		AccountDTO returnValue = new AccountDTO();
		BeanUtils.copyProperties(accountEntity, returnValue);

		return returnValue;
	}

	@Override
	public AccountDTO getAccountByName(String name) {

		AccountDTO returnValue = null;

		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against particular persistent class
		CriteriaQuery<AccountEntity> criteria = cb.createQuery(AccountEntity.class);

		// Query
		Root<AccountEntity> root = criteria.from(AccountEntity.class);
		criteria.select(root);
		criteria.where(cb.equal(root.get("name"), name));

		// Fetch single result
		Query<AccountEntity> query = session.createQuery(criteria);
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
	
	
	// SECURITY QUESTIONS

	@Override
	public List<SecretQuestionDTO> getSecretQuestions() {
		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against particular persistent class
		CriteriaQuery<SecretQuestionEntity> criteria = cb.createQuery(SecretQuestionEntity.class);

		// Query
		Root<SecretQuestionEntity> root = criteria.from(SecretQuestionEntity.class);
		criteria.select(root);

		// Fetch single result
		List<SecretQuestionEntity> searchResults = session.createQuery(criteria).getResultList();

		List<SecretQuestionDTO> returnValue = new ArrayList<SecretQuestionDTO>();
		for(SecretQuestionEntity securityQuestionEntity: searchResults) {
			SecretQuestionDTO securityQuestionDto = new SecretQuestionDTO();
			BeanUtils.copyProperties(securityQuestionEntity, securityQuestionDto);
			returnValue.add(securityQuestionDto);
		}

		return returnValue;
	}

	@Override
	public SecretQuestionDTO getSecretQuestion(String id) {
		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against particular persistent class
		CriteriaQuery<SecretQuestionEntity> criteria = cb.createQuery(SecretQuestionEntity.class);

		// Query
		Root<SecretQuestionEntity> root = criteria.from(SecretQuestionEntity.class);
		criteria.select(root);
		criteria.where(cb.equal(root.get("id"), id));

		// Fetch single result
		SecretQuestionEntity securityQuestionEntity = session.createQuery(criteria).getSingleResult();

		SecretQuestionDTO returnValue = new SecretQuestionDTO();
		BeanUtils.copyProperties(securityQuestionEntity, returnValue);

		return returnValue;
	}

	@Override
	public SecretQuestionDTO saveSecretQuestion(SecretQuestionDTO securityQuestionDto) {
		SecretQuestionDTO returnValue = new SecretQuestionDTO();
		SecretQuestionEntity securityQuestionEntity = new SecretQuestionEntity();
		BeanUtils.copyProperties(securityQuestionDto, securityQuestionEntity);

		session.beginTransaction();
		session.save(securityQuestionEntity);
		session.getTransaction().commit();

		BeanUtils.copyProperties(securityQuestionEntity, returnValue);

		return returnValue;
	}
	
	@Override
	public void updateSecretQuestion(SecretQuestionDTO securityQuestionDto) {
		SecretQuestionEntity securityQuestionEntity = new SecretQuestionEntity();
		BeanUtils.copyProperties(securityQuestionDto, securityQuestionEntity);

		session.beginTransaction();
		session.update(securityQuestionEntity);
		session.getTransaction().commit();

	}
	
	@Override
	public void deleteSecretQuestion(SecretQuestionDTO securityQuestionDto) {
		SecretQuestionEntity securityQuestionEntity = new SecretQuestionEntity();
		BeanUtils.copyProperties(securityQuestionDto, securityQuestionEntity);

		session.beginTransaction();
		session.delete(securityQuestionEntity);
		session.getTransaction().commit();

	}

	
	// BANKS

	@Override
	public List<BankDTO> getBanks() {
		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against particular persistent class
		CriteriaQuery<BankEntity> criteria = cb.createQuery(BankEntity.class);

		// Query
		Root<BankEntity> root = criteria.from(BankEntity.class);
		criteria.select(root);

		// Fetch single result
		List<BankEntity> searchResults = session.createQuery(criteria).getResultList();

		List<BankDTO> returnValue = new ArrayList<BankDTO>();
		for(BankEntity bankEntity: searchResults) {
			BankDTO bankDto = new BankDTO();
			BeanUtils.copyProperties(bankEntity, bankDto);
			
			// Secret questions
			if(bankEntity.getSecretQuestions().size()>0) {
				Set<SecretQuestionDTO> secretQuestionsDto = new HashSet<SecretQuestionDTO>();
				for(SecretQuestionEntity secretQuestionEntity: bankEntity.getSecretQuestions()) {
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
		CriteriaQuery<BankEntity> criteria = cb.createQuery(BankEntity.class);

		// Query
		Root<BankEntity> root = criteria.from(BankEntity.class);
		criteria.select(root);
		criteria.where(cb.equal(root.get("id"), id));

		// Fetch single result
		BankEntity bankEntity = session.createQuery(criteria).getSingleResult();

		BankDTO returnValue = new BankDTO();
		BeanUtils.copyProperties(bankEntity, returnValue);

		return returnValue;
	}

	@Override
	public BankDTO getBankByName(String name) {

		BankDTO returnValue = null;

		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against particular persistent class
		CriteriaQuery<BankEntity> criteria = cb.createQuery(BankEntity.class);

		// Query
		Root<BankEntity> root = criteria.from(BankEntity.class);
		criteria.select(root);
		criteria.where(cb.equal(root.get("name"), name));

		// Fetch single result
		Query<BankEntity> query = session.createQuery(criteria);
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
		
		for(SecretQuestionEntity secretQuestionEntity: bankEntity.getSecretQuestions())
			secretQuestionEntity.setBank(bankEntity);

		session.beginTransaction();
		session.save(bankEntity);
		session.getTransaction().commit();

		BeanUtils.copyProperties(bankEntity, returnValue);
		
		// Secret questions
		if(bankEntity.getSecretQuestions().size()>0) {
			Set<SecretQuestionDTO> secretQuestionsDto = new HashSet<SecretQuestionDTO>();
			for(SecretQuestionEntity secretQuestionEntity: bankEntity.getSecretQuestions()) {
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

		session.beginTransaction();
		session.update(bankEntity);
		session.getTransaction().commit();

	}
	
	@Override
	public void deleteBank(BankDTO bankDto) {
		BankEntity bankEntity = new BankEntity();
		BeanUtils.copyProperties(bankDto, bankEntity);

		session.beginTransaction();
		session.delete(bankEntity);
		session.getTransaction().commit();

	}

}