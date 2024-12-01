package com.SynClick.quiziniapp.Data.Models.RequestsModel;

import com.SynClick.quiziniapp.Data.Models.Conversation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetConversationByIdResponse {
    private String message;
    private Conversation conversation;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversations) {
        this.conversation = conversations;
    }
}
