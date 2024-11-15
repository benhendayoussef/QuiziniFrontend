package com.SynClick.quiziniapp.Data.Models.RequestsModel;

import com.SynClick.quiziniapp.Data.Models.Questionnaire;
import com.SynClick.quiziniapp.Data.Models.Topic;

import java.util.List;

public class getAllQuestionnairesResponse {
    String message;
    List<Questionnaire> Questionnaires;

    public getAllQuestionnairesResponse(String message, List<com.SynClick.quiziniapp.Data.Models.Questionnaire> questionnaire) {
        this.message = message;
        Questionnaires = questionnaire;
    }
    public getAllQuestionnairesResponse() {
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public List<Questionnaire> getQuestionnaire() {
        return Questionnaires;
    }
    public void setQuestionnaire(List<Questionnaire> questionnaire) {
        Questionnaires = questionnaire;
    }


}
