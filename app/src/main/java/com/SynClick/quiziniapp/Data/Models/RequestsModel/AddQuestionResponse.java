package com.SynClick.quiziniapp.Data.Models.RequestsModel;

import com.SynClick.quiziniapp.Data.Models.Conversation;

public class AddQuestionResponse {
    String message;
    Conversation conversation;

    public AddQuestionResponse() {
    }

    public AddQuestionResponse(String message, Conversation conversation) {
        this.message = message;
        this.conversation = conversation;
    }

    public String getMessage() {
        return message;
    }
    public Conversation getConversation() {
        return conversation;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

}
