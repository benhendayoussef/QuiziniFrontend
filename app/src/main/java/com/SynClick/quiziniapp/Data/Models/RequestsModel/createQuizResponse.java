package com.SynClick.quiziniapp.Data.Models.RequestsModel;

import com.SynClick.quiziniapp.Data.Models.Question;

import java.util.List;

public class createQuizResponse {
    private List<Question> questions;
    private String questionnaireId;

    public createQuizResponse(List<Question> questions, String questionnaireId) {
        this.questions = questions;
        this.questionnaireId = questionnaireId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public String getQuiz_id() {
        return questionnaireId;
    }
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    public void setQuiz_id(String questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

}
