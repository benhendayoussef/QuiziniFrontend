package com.SynClick.quiziniapp.Data.DAOs.serverSevices;


import com.SynClick.quiziniapp.Data.Models.RequestsModel.GetAllTopicResponse;
import com.SynClick.quiziniapp.Data.Models.RequestsModel.GetAllUserTopicResponse;
import com.SynClick.quiziniapp.Data.Models.RequestsModel.RequestResponse;
import com.SynClick.quiziniapp.Data.Models.userEntityDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TopicService {
    @GET("api/v1/topics/getAllTopics")
    Call<GetAllTopicResponse> getAllTopics(@Header("Authorization") String token);

    @GET("api/v1/topics/getAllUserTopics")
    Call<GetAllUserTopicResponse> getAllUserTopics(@Header("Authorization") String token);

    @GET("api/v1/topics/getAllActivatedUserTopics")
    Call<GetAllUserTopicResponse> getAllActivatedUserTopics(@Header("Authorization") String token);

}
