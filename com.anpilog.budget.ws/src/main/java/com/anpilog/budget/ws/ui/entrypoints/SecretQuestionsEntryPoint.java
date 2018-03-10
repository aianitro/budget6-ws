
package com.anpilog.budget.ws.ui.entrypoints;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.BeanUtils;

import com.anpilog.budget.ws.service.SecretQuestionsService;
import com.anpilog.budget.ws.service.impl.SecretQuestionsServiceImpl;
import com.anpilog.budget.ws.shared.dto.SecretQuestionDTO;
import com.anpilog.budget.ws.ui.model.request.CreateSecurityQuestionRequest;
import com.anpilog.budget.ws.ui.model.request.UpdateSecurityQuestionRequest;
import com.anpilog.budget.ws.ui.model.response.DeleteSecurityQuestionResponse;
import com.anpilog.budget.ws.ui.model.response.RequestOperation;
import com.anpilog.budget.ws.ui.model.response.ResponseStatus;
import com.anpilog.budget.ws.ui.model.response.SecurityQuestionResponse;

@Path("/securityQuestions")
public class SecretQuestionsEntryPoint {

	@GET
	@Produces({ MediaType.APPLICATION_JSON})
	public List<SecurityQuestionResponse> getSecurityQuestions() {

		SecretQuestionsService securityQuestionsService = new SecretQuestionsServiceImpl();
		List<SecretQuestionDTO> securityQuestions = securityQuestionsService.getSecurityQuestions();

		// Prepare return value
		List<SecurityQuestionResponse> returnValue = new ArrayList<SecurityQuestionResponse>();
		for (SecretQuestionDTO securityQuestionDto : securityQuestions) {
			SecurityQuestionResponse securityQuestionResponse = new SecurityQuestionResponse();
			BeanUtils.copyProperties(securityQuestionDto, securityQuestionResponse);
			returnValue.add(securityQuestionResponse);
		}

		return returnValue;
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public SecurityQuestionResponse getSecurityQuestion(@PathParam("id") String id) {
		SecurityQuestionResponse returnValue = null;

		SecretQuestionsService securityQuestionsService = new SecretQuestionsServiceImpl();
		SecretQuestionDTO securityQuestionDto = securityQuestionsService.getSecurityQuestion(id);

		returnValue = new SecurityQuestionResponse();
		BeanUtils.copyProperties(securityQuestionDto, returnValue);

		return returnValue;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public SecurityQuestionResponse createUser(CreateSecurityQuestionRequest requestObject) {
		SecurityQuestionResponse returnValue = new SecurityQuestionResponse();

		// Prepare SecurityQuestionDTO
		SecretQuestionDTO securityQuestionDto = new SecretQuestionDTO();
		BeanUtils.copyProperties(requestObject, securityQuestionDto);

		// Create new 
		SecretQuestionsService securityQuestionsService = new SecretQuestionsServiceImpl();
		SecretQuestionDTO createdSecurityQuestion = securityQuestionsService.createSecurityQuestion(securityQuestionDto);

		// Prepare response
		BeanUtils.copyProperties(createdSecurityQuestion, returnValue);

		return returnValue;
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public SecurityQuestionResponse updateSecurityQuestion(@PathParam("id") String id, UpdateSecurityQuestionRequest requestObject) {

		SecretQuestionsService securityQuestionsService = new SecretQuestionsServiceImpl();
		SecretQuestionDTO storedSecurityQuestion = securityQuestionsService.getSecurityQuestion(id);

		// Set only those fields you would like to be updated with this request
		if (requestObject.getQuestion() != null && !requestObject.getQuestion().isEmpty()) {
			storedSecurityQuestion.setQuestion(requestObject.getQuestion());
		}
		if (requestObject.getAnswer() != null && !requestObject.getAnswer().isEmpty()) {
			storedSecurityQuestion.setAnswer(requestObject.getAnswer());
		}

		// Update User Details
		securityQuestionsService.updateSecurityQuestion(storedSecurityQuestion);

		// Prepare return value
		SecurityQuestionResponse returnValue = new SecurityQuestionResponse();
		BeanUtils.copyProperties(storedSecurityQuestion, returnValue);

		return returnValue;
	}
	
    //@Secured
    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public DeleteSecurityQuestionResponse deleteSecurityQuestion(@PathParam("id") String id) {
        DeleteSecurityQuestionResponse returnValue = new DeleteSecurityQuestionResponse();
        returnValue.setRequestOperation(RequestOperation.DELETE);
        
        SecretQuestionsService securityQuestionsService = new SecretQuestionsServiceImpl();
        SecretQuestionDTO storedUserDetails = securityQuestionsService.getSecurityQuestion(id);
 
        securityQuestionsService.deleteSecurityQuestion(storedUserDetails);

        returnValue.setResponseStatus(ResponseStatus.SUCCESS);
 
        return returnValue;
    }

}
