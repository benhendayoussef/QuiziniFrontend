package com.SynClick.quiziniapp.Data.DAOs.serverSevices;


import com.SynClick.quiziniapp.Data.Models.RequestsModel.GetAllTopicResponse;
import com.SynClick.quiziniapp.Data.Models.RequestsModel.GetAllUserTopicResponse;
import com.SynClick.quiziniapp.Data.Models.RequestsModel.getNewPostsRequest;
import com.SynClick.quiziniapp.Data.Models.RequestsModel.getNewPostsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PostService {
    @POST("api/v1/posts/getNewPosts")
    Call<getNewPostsResponse> getNewPosts(@Header("Authorization") String token, @Body getNewPostsRequest getNewPostsRequest);

}
