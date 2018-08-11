package com.anpilog.budget.ws.service.impl;

import java.util.List;

import com.anpilog.budget.ws.exceptions.CouldNotCreateRecordException;
import com.anpilog.budget.ws.exceptions.CouldNotDeleteRecordException;
import com.anpilog.budget.ws.exceptions.CouldNotUpdateRecordException;
import com.anpilog.budget.ws.exceptions.EmailVerificationException;
import com.anpilog.budget.ws.exceptions.NoRecordFoundException;
import com.anpilog.budget.ws.io.dao.DAO;
import com.anpilog.budget.ws.service.UsersService;
import com.anpilog.budget.ws.shared.dto.UserDTO;
import com.anpilog.budget.ws.ui.model.response.ErrorMessages;
import com.anpilog.budget.ws.utils.AmazonSES;
import com.anpilog.budget.ws.utils.UserUtils;


public class UsersServiceImpl implements UsersService {

	DAO database;
	
	public UsersServiceImpl() {
	}

	public UsersServiceImpl(DAO database) {
		this.database = database;
	}

	UserUtils userProfileUtils = new UserUtils();

	@Override
	public List<UserDTO> getUsers(int start, int limit) {

		List<UserDTO> users = null;

		// Get users from database
		try {
			this.database.openConnection();
			users = this.database.getUsers(start, limit);
		} finally {
			this.database.closeConnection();
		}

		return users;
	}

	public UserDTO getUser(String id) {
		UserDTO returnValue = null;
		try {
			this.database.openConnection();
			returnValue = this.database.getUser(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		} finally {
			this.database.closeConnection();
		}
		return returnValue;
	}

	@Override
	public UserDTO getUserByUserName(String userName) {
		UserDTO userDto = null;

		if (userName == null || userName.isEmpty()) {
			return userDto;
		}

		// Connect to database
		try {
			this.database.openConnection();
			userDto = this.database.getUserByUserName(userName);
		} finally {
			this.database.closeConnection();
		}

		return userDto;
	}

	public UserDTO createUser(UserDTO user) {
		UserDTO returnValue = null;

		// Validate the required fields
		userProfileUtils.validateRequiredFields(user);

		// Check if user already exists
		UserDTO existingUser = this.getUserByUserName(user.getEmail());
		if (existingUser != null) {
			throw new CouldNotCreateRecordException(ErrorMessages.RECORD_ALREADY_EXISTS.name());
		}

		// Generate secure public user id
		String userId = userProfileUtils.generateUserId(30);
		user.setUserId(userId);

		// Generate salt
		String salt = userProfileUtils.getSalt(30);
		// Generate secure password
		String encryptedPassword = userProfileUtils.generateSecurePassword(user.getPassword(), salt);
		user.setSalt(salt);
		user.setEncryptedPassword(encryptedPassword);
		user.setEmailVerificationStatus(false);
		user.setEmailVerificationToken(userProfileUtils.generateEmailverificationToken(30));

		// Record data into a database
		returnValue = this.saveUser(user);

		new AmazonSES().verifyEmail(user);

		// Return back the user profile
		return returnValue;
	}

	public void updateUserDetails(UserDTO userDetails) {
		try {
			// Connect to database
			this.database.openConnection();
			// Update User Details
			this.database.updateUser(userDetails);

		} catch (Exception ex) {
			throw new CouldNotUpdateRecordException(ex.getMessage());
		} finally {
			this.database.closeConnection();
		}
	}

	private UserDTO saveUser(UserDTO user) {
		UserDTO returnValue = null;
		// Connect to database
		try {
			this.database.openConnection();
			returnValue = this.database.saveUser(user);
		} finally {
			this.database.closeConnection();
		}

		return returnValue;
	}

	public void deleteUser(UserDTO userDto) {
		try {
			this.database.openConnection();
			this.database.deleteUser(userDto);
		} catch (Exception ex) {
			throw new CouldNotDeleteRecordException(ex.getMessage());
		} finally {
			this.database.closeConnection();
		}

		// Verify that user is deleted
		try {
			userDto = getUser(userDto.getUserId());
		} catch (NoRecordFoundException ex) {
			userDto = null;
		}

		if (userDto != null) {
			throw new CouldNotDeleteRecordException(ErrorMessages.COULD_NOT_DELETE_RECORD.getErrorMessage());
		}
	}

	@Override
	public boolean verifyEmail(String token) {
		boolean returnValue = false;

		if (token == null || token.isEmpty())
			throw new EmailVerificationException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		try {
			UserDTO storedUser = getUserByEmailToken(token);

			if (storedUser == null) {
				return returnValue;
			}

			// Update user record
			storedUser.setEmailVerificationStatus(true);
			storedUser.setEmailVerificationToken(null);

			updateUserDetails(storedUser);

			returnValue = true;

		} catch (Exception ex) {
			throw new EmailVerificationException(ex.getMessage());
		}

		return returnValue;
	}

	private UserDTO getUserByEmailToken(String token) {
		UserDTO returnValue;

		try {
			this.database.openConnection();
			returnValue = this.database.getUserByEmailToken(token);
		} finally {
			this.database.closeConnection();
		}

		return returnValue;
	}

}
