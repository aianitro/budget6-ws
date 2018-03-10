package com.anpilog.budget.ws.filters;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.anpilog.budget.ws.annotations.Secured;
import com.anpilog.budget.ws.exceptions.AuthenticationException;
import com.anpilog.budget.ws.service.UsersService;
import com.anpilog.budget.ws.shared.dto.UserDTO;
import com.anpilog.budget.ws.utils.UserUtils;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
	
	@Autowired
	UsersService usersService;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// Extract Authorization header details
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
			throw new AuthenticationException("Authorization header must be provided");
		}

		// Extract the token
		String token = authorizationHeader.substring("Bearer".length()).trim();

		// Extract user id
		String userId = requestContext.getUriInfo().getPathParameters().getFirst("id");

		validateToken(token, userId);

	}

	private void validateToken(String token, String userId) {
		// Get user profile details		
		UserDTO userProfile = usersService.getUser(userId);

		// Assemble access token using two parts. One from DB and one from HTTP request
		String completeToken = userProfile.getToken() + token;

		// Create access token material out of the userId received and salt kept in DB
		String securePassword = userProfile.getEncryptedPassword();
		String salt = userProfile.getSalt();
		String accessTokenMaterial = userId + salt;
		byte[] encryptedAccessToken = null;

		try {
			encryptedAccessToken = new UserUtils().encrypt(securePassword, accessTokenMaterial);
		} catch (InvalidKeySpecException ex) {
			Logger.getLogger(AuthenticationFilter.class.getName()).log(Level.SEVERE, null, ex);
			throw new AuthenticationException("Failed to issue secure access token");
		}

		String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);

		// Compare two access tokens
		if (!encryptedAccessTokenBase64Encoded.equalsIgnoreCase(completeToken))
			throw new AuthenticationException("Authorization token did not match");
	}

}
