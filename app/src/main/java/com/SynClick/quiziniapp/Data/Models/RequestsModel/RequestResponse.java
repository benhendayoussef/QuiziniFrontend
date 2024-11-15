package com.SynClick.quiziniapp.Data.Models.RequestsModel;

public class RequestResponse {
    String message,token;

    public RequestResponse() {
    }

    public RequestResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
