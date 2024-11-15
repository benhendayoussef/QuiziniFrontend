package com.SynClick.quiziniapp.Data.Models.RequestsModel;

import com.SynClick.quiziniapp.Data.Models.Topic;

import java.util.List;

public class GetAllUserTopicResponse {
    String message;
    List<Topic> userTopics;

    public GetAllUserTopicResponse() {
    }

    public GetAllUserTopicResponse(String message, List<Topic> topics) {
        this.message = message;
        this.userTopics = topics;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Topic> getUserTopics() {
        return userTopics;
    }

    public void setUserTopics(List<Topic> topics) {
        this.userTopics = topics;
    }
}
