package com.SynClick.quiziniapp.Data.Models.RequestsModel;

public class CreateQuestionRequest {
    String question;

    public CreateQuestionRequest(String question) {
        this.question = question;
    }

    public CreateQuestionRequest() {
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
