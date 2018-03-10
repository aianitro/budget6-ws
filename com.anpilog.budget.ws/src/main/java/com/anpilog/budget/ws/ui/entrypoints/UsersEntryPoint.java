package com.anpilog.budget.ws.ui.entrypoints;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.anpilog.budget.ws.annotations.Secured;
import com.anpilog.budget.ws.service.UsersService;
import com.anpilog.budget.ws.shared.dto.UserDTO;
import com.anpilog.budget.ws.ui.model.request.CreateUserRequest;
import com.anpilog.budget.ws.ui.model.request.UpdateUserRequest;
import com.anpilog.budget.ws.ui.model.response.DeleteUserResponse;
import com.anpilog.budget.ws.ui.model.response.RequestOperation;
import com.anpilog.budget.ws.ui.model.response.ResponseStatus;
import com.anpilog.budget.ws.ui.model.response.UserResponse;

@Path("/users")
public class UsersEntryPoint {

	@Autowired
	UsersService userService;
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<UserResponse> getUsers(@DefaultValue("0") @QueryParam("start") int start,
			@DefaultValue("50") @QueryParam("limit") int limit) {
		
		List<UserDTO> users = userService.getUsers(start, limit);

		// Prepare return value
		List<UserResponse> returnValue = new ArrayList<UserResponse>();
		for (UserDTO userDto : users) {
			UserResponse userModel = new UserResponse();
			BeanUtils.copyProperties(userDto, userModel);
			userModel.setHref("/users/" + userDto.getUserId());
			returnValue.add(userModel);
		}

		return returnValue;
	}

	@Secured
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public UserResponse getUserProfile(@PathParam("id") String id) {
		UserResponse returnValue = null;

		UserDTO userProfile = userService.getUser(id);

		returnValue = new UserResponse();
		BeanUtils.copyProperties(userProfile, returnValue);

		return returnValue;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public UserResponse createUser(CreateUserRequest requestObject) {
		UserResponse returnValue = new UserResponse();

		// Prepare UserDTO
		UserDTO userDto = new UserDTO();
		BeanUtils.copyProperties(requestObject, userDto);

		// Create new user
		UserDTO createdUserProfile = userService.createUser(userDto);

		// Prepare response
		BeanUtils.copyProperties(createdUserProfile, returnValue);

		return returnValue;
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public UserResponse updateUserDetails(@PathParam("id") String id, UpdateUserRequest userDetails) {

		UserDTO storedUserDetails = userService.getUser(id);

		// Set only those fields you would like to be updated with this request
		if (userDetails.getFirstName() != null && !userDetails.getFirstName().isEmpty()) {
			storedUserDetails.setFirstName(userDetails.getFirstName());
		}
		storedUserDetails.setLastName(userDetails.getLastName());

		// Update User Details
		userService.updateUserDetails(storedUserDetails);

		// Prepare return value
		UserResponse returnValue = new UserResponse();
		BeanUtils.copyProperties(storedUserDetails, returnValue);

		return returnValue;
	}
	
    //@Secured
    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public DeleteUserResponse deleteUserProfile(@PathParam("id") String id) {
        DeleteUserResponse returnValue = new DeleteUserResponse();
        returnValue.setRequestOperation(RequestOperation.DELETE);

        UserDTO storedUserDetails = userService.getUser(id);
 
        userService.deleteUser(storedUserDetails);

        returnValue.setResponseStatus(ResponseStatus.SUCCESS);
 
        return returnValue;
    }

}
