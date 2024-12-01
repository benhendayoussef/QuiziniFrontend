package com.SynClick.quiziniapp.Data.Models.RequestsModel;

public class ReactionMessageRequest {
    Long conversationId,messagePairId;

    public ReactionMessageRequest(Long conversationId, Long messagePairId) {
        this.conversationId = conversationId;
        this.messagePairId = messagePairId;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public Long getMessagePairId() {
        return messagePairId;
    }

    public void setMessagePairId(Long messagePairId) {
        this.messagePairId = messagePairId;
    }
}
