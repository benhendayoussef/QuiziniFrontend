package com.SynClick.quiziniapp.Data.Models.RequestsModel;

import java.util.List;

public class GetConversationByIdRequest {
    Long messagePairQt;
    List<Long> existedMessagePairsId;
    Long conversationId;

    public GetConversationByIdRequest(Long messagePairQt, List<Long> existedMessagePairsId, Long conversationId) {
        this.messagePairQt = messagePairQt;
        this.existedMessagePairsId = existedMessagePairsId;
        this.conversationId = conversationId;
    }

}
