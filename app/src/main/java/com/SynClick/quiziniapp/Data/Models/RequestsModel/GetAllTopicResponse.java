package com.SynClick.quiziniapp.Data.Models.RequestsModel;

import com.SynClick.quiziniapp.Data.Models.Topic;

import java.util.List;

public class GetAllTopicResponse {
    String message;
    List<Topic> topics;

    public GetAllTopicResponse() {
    }

    public GetAllTopicResponse(String message, List<Topic> topics) {
        this.message = message;
        this.topics = topics;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }
}
