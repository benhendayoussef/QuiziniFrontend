package com.SynClick.quiziniapp.Data.DAOs.serverSevices;


import com.SynClick.quiziniapp.Data.Models.RequestsModel.CreateQuizRequest;
import com.SynClick.quiziniapp.Data.Models.RequestsModel.createQuizResponse;
import com.SynClick.quiziniapp.Data.Models.RequestsModel.getAllQuestionnairesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface QuestionnaireService {
    @GET("api/v1/questionnaire/getAllQuestionnaires")
    Call<getAllQuestionnairesResponse> getAllQuestionnaires(@Header("Authorization") String token);
    @POST("api/v1/questionnaire/getQuestionnaire")
    Call<createQuizResponse> getQuestionnaire(@Header("Authorization") String token, @Body CreateQuizRequest createQuizRequest);

}
