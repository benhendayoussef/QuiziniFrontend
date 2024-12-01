package com.SynClick.quiziniapp.Data.DAOs.serverSevices;

import com.SynClick.quiziniapp.Data.Models.Conversation;

public class Services {

    public static ClientService clientService;
    public static TopicService topicService;
    public static QuestionnaireService questionnaireService;
    public static PostService postService;
    public static ChatBotService chatBotService;





    public static ChatBotService getChatBotService() {
        return chatBotService;
    }

    public static void setChatBotService(ChatBotService chatBotService) {
        Services.chatBotService = chatBotService;
    }



    public static TopicService getTopicService() {
        return topicService;
    }

    public static void setTopicService(TopicService topicService) {
        Services.topicService = topicService;
    }


    public static ClientService getClientService() {
        return clientService;
    }

    public static void setClientService(ClientService clientService) {
        Services.clientService = clientService;
    }

    public static QuestionnaireService getQuestionnaireService() {
        return questionnaireService;
    }

    public static void setQuestionnaireService(QuestionnaireService questionnaireService) {
        Services.questionnaireService = questionnaireService;
    }

    public static PostService getPostService() {
        return postService;
    }
    public static void setPostService(PostService postService) {
        Services.postService = postService;
    }

}
