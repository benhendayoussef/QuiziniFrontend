package com.SynClick.quiziniapp.Data.Models;

import java.util.List;

public class QuestionReponse {
    String questionId;
    int respondingTime;

    List<Integer> answersId;
    public QuestionReponse(String questionId, int respondingTime, List<Integer> answersId) {
        this.questionId = questionId;
        this.respondingTime = respondingTime;
        this.answersId = answersId;
    }
    public String getQuestionId() {
        return questionId;
    }
    public int getRespondingTime() {
        return respondingTime;
    }
    public List<Integer> getAnswersId() {
        return answersId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setRespondingTime(int respondingTime) {
        this.respondingTime = respondingTime;
    }

    public void setAnswersId(List<Integer> answersId) {
        this.answersId = answersId;
    }
}
