package com.SynClick.quiziniapp.Data.Models.RequestsModel;

import java.util.List;

public class CreateQuizRequest {
    private int questionQuantity;
    private List<Integer> topicsId;

    public CreateQuizRequest(int questionQuantity, List<Integer> topicsId) {
        this.questionQuantity = questionQuantity;
        this.topicsId = topicsId;
    }

    public int getQuestionQuantity() {
        return questionQuantity;
    }

    public void setQuestionQuantity(int questionQuantity) {
        this.questionQuantity = questionQuantity;
    }

    public List<Integer> getTopicsId() {
        return topicsId;
    }

    public void setTopicsId(List<Integer> topicsId) {
        this.topicsId = topicsId;
    }
}