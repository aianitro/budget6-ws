package com.anpilog.budget.ws.service;

import java.util.List;

import com.anpilog.budget.ws.shared.dto.UserDTO;

public interface UsersService {
	List<UserDTO> getUsers(int start, int limit);
    UserDTO getUser(String id);
    UserDTO getUserByUserName(String username);
    UserDTO createUser(UserDTO user);
    void updateUserDetails(UserDTO userDetails);
    void deleteUser(UserDTO userDto);
    boolean verifyEmail(String token);
}
