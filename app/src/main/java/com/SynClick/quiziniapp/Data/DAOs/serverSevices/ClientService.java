package com.SynClick.quiziniapp.Data.DAOs.serverSevices;


import com.SynClick.quiziniapp.Data.Models.RequestsModel.RequestResponse;
import com.SynClick.quiziniapp.Data.Models.userEntityDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ClientService {
    @POST("api/v1/auth/signIn")
    Call<RequestResponse> signIn(@Body userEntityDto userEntityDtoInfo);
    @POST("api/v1/users/saveSelectedTopics")
    Call<RequestResponse> saveSelectedTopics(@Header("Authorization") String token,@Body userEntityDto user);
    @POST("api/v1/users/updateUserRanks")
    Call<RequestResponse> updateUserRanks(@Header("Authorization") String token, @Body List<Map<String, Object> > topics);

}
