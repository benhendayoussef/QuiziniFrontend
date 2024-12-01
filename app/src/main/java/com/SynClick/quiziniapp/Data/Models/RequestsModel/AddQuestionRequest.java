package com.SynClick.quiziniapp.Data.Models.RequestsModel;

public class AddQuestionRequest {
    Long conversationId;
    String question;

    public AddQuestionRequest(Long conversationId, String question) {
        this.conversationId = conversationId;
        this.question = question;
    }

    public AddQuestionRequest() {
    }

    public Long getConversationId() {
        return conversationId;
    }

    public String getQuestion() {
        return question;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
