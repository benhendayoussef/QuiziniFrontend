package com.SynClick.quiziniapp.Data.Models;

import java.util.List;

public class Conversation {
    Long id;
    String title,createdAt,updatedAt,userId;
    List<Message> messages;
    Boolean valid;

    public Conversation() {
    }

    public Conversation(Long id, String title, String createdAt, String updatedAt, String userId, List<Message> messages, Boolean valid) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.messages = messages;
        this.valid = valid;
    }

    public Conversation(String title, String createdAt, String updatedAt, String userId, Boolean valid) {
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.valid = valid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
