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
public class GetConversationResponse {
    private String message;
    private List<Conversation> conversations;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }
}
