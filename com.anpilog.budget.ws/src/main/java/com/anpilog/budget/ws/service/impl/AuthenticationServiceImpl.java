package com.anpilog.budget.ws.service.impl;

import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.anpilog.budget.ws.exceptions.AuthenticationException;
import com.anpilog.budget.ws.exceptions.EmailVerificationException;
import com.anpilog.budget.ws.io.dao.DAO;
import com.anpilog.budget.ws.io.dao.impl.MySQLDAO;
import com.anpilog.budget.ws.service.AuthenticationService;
import com.anpilog.budget.ws.service.UsersService;
import com.anpilog.budget.ws.shared.dto.UserDTO;
import com.anpilog.budget.ws.ui.model.response.ErrorMessages;
import com.anpilog.budget.ws.utils.UserUtils;

public class AuthenticationServiceImpl implements AuthenticationService{
	
	@Autowired
	UsersService usersService;
	DAO database;

	@Override
	public UserDTO authenticate(String username, String password) throws AuthenticationException {
		UserDTO storedUser = usersService.getUserByUserName(username);
		
		if(storedUser==null) {
			throw new AuthenticationException(ErrorMessages.AUTHENTICAION_FAILED.getErrorMessage());
		}
		
		if(!storedUser.getEmailVerificationStatus()){
			throw new EmailVerificationException(ErrorMessages.EMAIL_ADDRESS_NOT_VERIFIED.getErrorMessage());
		}
		
		boolean isAuthenticated = false;
		String encryptedPassword = new UserUtils().generateSecurePassword(password, storedUser.getSalt());		
		if(encryptedPassword !=null && encryptedPassword.equalsIgnoreCase(storedUser.getEncryptedPassword())) {
			if(username != null && username.equalsIgnoreCase(storedUser.getEmail())) {
				isAuthenticated = true;				
			}
		}
		
		if(!isAuthenticated)
			new AuthenticationException(ErrorMessages.AUTHENTICAION_FAILED.getErrorMessage());
		
		return storedUser;
	}

	@Override
	public String issueAccessToken(UserDTO userProfile) throws AuthenticationException {
		String returnValue = null;
		
		String newSaltAsPostfix = userProfile.getSalt();
		String accessTokenMaterial = userProfile.getUserId() + newSaltAsPostfix;
		
		byte[] encryptedAccessToken = null;
		try {
			encryptedAccessToken = new UserUtils().encrypt(userProfile.getEncryptedPassword(), accessTokenMaterial);
		}catch (InvalidKeySpecException e) {
			Logger.getLogger(AuthenticationServiceImpl.class.getName()).log(Level.SEVERE, null, e);
			throw new AuthenticationException("Failed to issue secure access token");
		}
		
		String ecryptedAccessTokenBased64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);
		
		// Split token intoequal parts
		int tokenLength = ecryptedAccessTokenBased64Encoded.length();
		
		String tokenToSaveToDatabase = ecryptedAccessTokenBased64Encoded.substring(0, tokenLength/2);
		returnValue = ecryptedAccessTokenBased64Encoded.substring(tokenLength/2, tokenLength);
		
		userProfile.setToken(tokenToSaveToDatabase);
		
		updateUserProfile(userProfile);
		
		return returnValue;
	}

	private void updateUserProfile(UserDTO userProfile) {
		this.database = new MySQLDAO();
		try {
			database.openConnection();
			database.updateUser(userProfile);
		}finally {
			database.closeConnection();			
		}
	}

	@Override
	public void resetSecurityCredentials(String password, UserDTO userProfile) {
		// Generate new salt
		UserUtils userUtils = new UserUtils();
		String salt = userUtils.getSalt(30);
		
		// Generate new password
		String securePassword = userUtils.generateSecurePassword(password, salt);
		userProfile.setSalt(salt);
		userProfile.setEncryptedPassword(securePassword);
		
		// Update user profile
		updateUserProfile(userProfile);
	}
}
