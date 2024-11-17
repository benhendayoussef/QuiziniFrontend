package com.SynClick.quiziniapp.Data;

import com.SynClick.quiziniapp.Data.Models.Questionnaire;
import com.SynClick.quiziniapp.Data.Models.RequestsModel.createQuizResponse;
import com.SynClick.quiziniapp.Data.Models.Topic;
import com.SynClick.quiziniapp.Data.Models.userEntityDto;

import java.util.List;

public class Data {
    public static String token ="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJub3N0YS5kb2VAZXhhbXBsZS5jb20iLCJleHAiOjE3MzI5OTQzNDgsImlhdCI6MTczMTc4NDc0OH0.PCfhb5CGh0mxQbhhEdkUvuSyLz6UyQUyMvD9TjSSPoY";
    public static userEntityDto user=new userEntityDto("test","Youssef","world","","",null,"",false,null,null,null,null,null,token);
    public static List<Topic> topics;
    public static List<Topic> userTopics;
    public static List<Questionnaire> Questionnaires;

    public static createQuizResponse ActualQQuestionnaire;
}
