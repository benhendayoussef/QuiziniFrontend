package com.SynClick.quiziniapp.Data.Models.RequestsModel;

import com.SynClick.quiziniapp.Data.Models.userEntityDto;

public class RequestResponse {
    String message;
    userEntityDto userDetails;

    public RequestResponse() {
    }

    public RequestResponse(String message, userEntityDto userDetails) {
        this.message = message;
        this.userDetails = userDetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public userEntityDto getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(userEntityDto userDetails) {
        this.userDetails = userDetails;
    }
}
