package com.SynClick.quiziniapp.Data.DAOs.serverSevices;

public class Services {

    public static ClientService clientService;
    public static TopicService topicService;


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
}
