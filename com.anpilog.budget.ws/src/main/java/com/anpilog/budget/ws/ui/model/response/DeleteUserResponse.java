package com.anpilog.budget.ws.ui.model.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeleteUserResponse {
    private RequestOperation requestOperation;
    private ResponseStatus responseStatus;

    /**
     * @return the requestOperation
     */
    public RequestOperation getRequestOperation() {
        return requestOperation;
    }

    /**
     * @param requestOperation the requestOperation to set
     */
    public void setRequestOperation(RequestOperation requestOperation) {
        this.requestOperation = requestOperation;
    }

    /**
     * @return the responseStatus
     */
    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    /**
     * @param responseStatus the responseStatus to set
     */
    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
    
}