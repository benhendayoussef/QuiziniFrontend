package com.SynClick.quiziniapp.Data.Models;

import java.util.List;

public class Questionnaire {
    int id,numberOfQuestions,numberCorrectedAnswers;
    String creationTime,description,answeredAt,userId;
    Boolean completed;

    public Questionnaire(int id, int numberOfQuestions, int numberCorrectedAnswers, String creationTime, String description, String answeredAt, String userId, Boolean completed) {
        this.id = id;
        this.numberOfQuestions = numberOfQuestions;
        this.numberCorrectedAnswers = numberCorrectedAnswers;
        this.creationTime = creationTime;
        this.description = description;
        this.answeredAt = answeredAt;
        this.userId = userId;
        this.completed = completed;
    }

    public Questionnaire() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getNumberCorrectedAnswers() {
        return numberCorrectedAnswers;
    }

    public void setNumberCorrectedAnswers(int numberCorrectedAnswers) {
        this.numberCorrectedAnswers = numberCorrectedAnswers;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnsweredAt() {
        return answeredAt;
    }

    public void setAnsweredAt(String answeredAt) {
        this.answeredAt = answeredAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
