package com.SynClick.quiziniapp.Data.Models.RequestsModel;

import com.SynClick.quiziniapp.Data.Models.Questionnaire;

import java.util.List;


public class CorrectQuestionnaireResponse {
    String message;
    Questionnaire questionnaire;
    public CorrectQuestionnaireResponse(String message, Questionnaire questionnaire) {
        this.message = message;
        this.questionnaire = questionnaire;
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
        return this.questionnaire;
    }
    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }


}
