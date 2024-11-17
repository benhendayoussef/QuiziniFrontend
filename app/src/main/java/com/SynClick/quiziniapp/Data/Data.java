package com.SynClick.quiziniapp.Data;

import com.SynClick.quiziniapp.Data.Models.Questionnaire;
import com.SynClick.quiziniapp.Data.Models.RequestsModel.createQuizResponse;
import com.SynClick.quiziniapp.Data.Models.Topic;
import com.SynClick.quiziniapp.Data.Models.userEntityDto;

import java.util.List;

public class Data {
    public static String token ="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5b3Vzc2VmMUBleGFtcGxlLmNvbSIsImV4cCI6MTczMzA1MDU3MCwiaWF0IjoxNzMxODQwOTcwfQ.PiLTJquRJpoLPpoavysFUc-5QTeXWMV7DxEUIVEh1r4";
    public static userEntityDto user=new userEntityDto("test","Youssef","world","","",null,"",false,null,null,null,null,null,token);
    public static List<Topic> topics;
    public static List<Topic> userTopics;
    public static List<Questionnaire> Questionnaires;

    public static createQuizResponse ActualQQuestionnaire;
}
