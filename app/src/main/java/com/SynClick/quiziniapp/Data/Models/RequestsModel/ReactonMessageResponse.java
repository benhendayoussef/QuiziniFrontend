package com.SynClick.quiziniapp.Data.Models.RequestsModel;

import com.SynClick.quiziniapp.Data.Models.Message;

public class ReactonMessageResponse {
    Message messagePair;
    String message;

    public ReactonMessageResponse(Message messagePair, String message) {
        this.messagePair = messagePair;
        this.message = message;
    }

    public Message getMessagePair() {
        return messagePair;
    }

    public void setMessagePair(Message messagePair) {
        this.messagePair = messagePair;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
