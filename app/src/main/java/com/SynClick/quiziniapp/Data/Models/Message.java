package com.SynClick.quiziniapp.Data.Models;


public class Message {
    Long id;
    String question,answer,createdAt,updatedAt;
    Boolean valid;
    int isLiked;

    public Message() {
    }

    public Message(Long id, String question, String answer, String createdAt, String updatedAt, Boolean valid, int isLiked) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.valid = valid;
        this.isLiked = isLiked;
    }

    public Message(String question, String answer, String createdAt, String updatedAt, Boolean valid, int isLiked) {
        this.question = question;
        this.answer = answer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.valid = valid;
        this.isLiked = isLiked;
    }

    public int getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
