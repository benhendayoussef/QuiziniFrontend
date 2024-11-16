package com.SynClick.quiziniapp.Data.Models.RequestsModel;

import com.SynClick.quiziniapp.Data.Models.QuestionReponse;

import java.util.List;

public class CorrectQuestionnaireRequest {
    int questionnaireId;

    List<QuestionReponse> questions;

    public CorrectQuestionnaireRequest() {
    }

    public CorrectQuestionnaireRequest(int questionnaireId, List<QuestionReponse> questions) {
        this.questionnaireId = questionnaireId;
        this.questions = questions;
    }

    public int getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public List<QuestionReponse> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionReponse> questions) {
        this.questions = questions;
    }


}
