package com.anpilog.budget.ws.service;

import com.anpilog.budget.ws.exceptions.AuthenticationException;
import com.anpilog.budget.ws.shared.dto.UserDTO;

public interface AuthenticationService {
	UserDTO authenticate(String username, String password) throws AuthenticationException;
	String issueAccessToken(UserDTO userProfile) throws AuthenticationException;
	void resetSecurityCredentials(String password, UserDTO authenticatedUser);
}
