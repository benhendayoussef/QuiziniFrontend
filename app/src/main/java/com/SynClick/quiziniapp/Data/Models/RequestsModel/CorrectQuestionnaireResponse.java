package com.SynClick.quiziniapp.Data.Models.RequestsModel;

import com.SynClick.quiziniapp.Data.Models.Questionnaire;

import java.util.List;


public class CorrectQuestionnaireResponse {
    String message;
    Questionnaire Questionnaire;
    public CorrectQuestionnaireResponse(String message, Questionnaire Questionnaire) {
        this.message = message;
        this.Questionnaire = Questionnaire;
    }

    public CorrectQuestionnaireResponse() {
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Questionnaire getQuestionnaire() {
        return this.Questionnaire;
    }
    public void setQuestionnaire(Questionnaire Questionnaire) {
        this.Questionnaire = Questionnaire;
    }


}
