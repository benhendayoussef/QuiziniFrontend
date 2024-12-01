package com.SynClick.quiziniapp.Data.Models.RequestsModel;

import java.util.List;

public class GetConversationRequest {
    Long conversationsQt;
    List<Long> existedConversationsId;

    public GetConversationRequest(Long conversationsQt, List<Long> existedConversationsId) {
        this.conversationsQt = conversationsQt;
        this.existedConversationsId = existedConversationsId;
    }

}
