package com.SynClick.quiziniapp.Data.Models;

import java.util.HashMap;

public class Question {

    int difficulty_level;
    String createdAt,question,hint,topicName,id,explanation,updatedAt;
    HashMap<String,String> propositions;

    public Question() {
    }

    public Question(int difficulty_level, String createdAt, String question, String hint, String topicName, String id, String explanation, String updatedAt, HashMap<String, String> propositions) {
        this.difficulty_level = difficulty_level;
        this.createdAt = createdAt;
        this.question = question;
        this.hint = hint;
        this.topicName = topicName;
        this.id = id;
        this.explanation = explanation;
        this.updatedAt = updatedAt;
        this.propositions = propositions;
    }

    public int getDifficulty_level() {
        return difficulty_level;
    }

    public void setDifficulty_level(int difficulty_level) {
        this.difficulty_level = difficulty_level;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public HashMap<String, String> getPropositions() {
        return propositions;
    }

    public void setPropositions(HashMap<String, String> propositions) {
        this.propositions = propositions;
    }
}
