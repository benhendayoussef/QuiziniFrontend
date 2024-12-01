package com.SynClick.quiziniapp.Data.DAOs.serverSevices;


import com.SynClick.quiziniapp.Data.Models.RequestsModel.AddQuestionRequest;
import com.SynClick.quiziniapp.Data.Models.RequestsModel.AddQuestionResponse;
import com.SynClick.quiziniapp.Data.Models.RequestsModel.CreateQuestionRequest;
import com.SynClick.quiziniapp.Data.Models.RequestsModel.GetConversationByIdRequest;
import com.SynClick.quiziniapp.Data.Models.RequestsModel.GetConversationByIdResponse;
import com.SynClick.quiziniapp.Data.Models.RequestsModel.GetConversationRequest;
import com.SynClick.quiziniapp.Data.Models.RequestsModel.GetConversationResponse;
import com.SynClick.quiziniapp.Data.Models.RequestsModel.ReactionMessageRequest;
import com.SynClick.quiziniapp.Data.Models.RequestsModel.ReactonMessageResponse;
import com.SynClick.quiziniapp.Data.Models.RequestsModel.VotePostByIdResponse;
import com.SynClick.quiziniapp.Data.Models.RequestsModel.getNewPostsRequest;
import com.SynClick.quiziniapp.Data.Models.RequestsModel.getNewPostsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChatBotService {
    @POST("api/v1/conversations/getConversations")
    Call<GetConversationResponse> getConversations(@Header("Authorization") String token, @Body GetConversationRequest getConversationRequest);
    @POST("api/v1/conversations/addQuestion")
    Call<AddQuestionResponse> addQuestion(@Header("Authorization") String token, @Body AddQuestionRequest getConversationRequest);
    @POST("api/v1/conversations/createConversation")
    Call<AddQuestionResponse> createConversation(@Header("Authorization") String token, @Body CreateQuestionRequest question);
    @POST("api/v1/conversations/dislikeAnswer")
    Call<ReactonMessageResponse> dislikeAnswer(@Header("Authorization") String token, @Body ReactionMessageRequest reaction);
    @POST("api/v1/conversations/likeAnswer")
    Call<ReactonMessageResponse> likeAnswer(@Header("Authorization") String token, @Body ReactionMessageRequest reaction);
    @POST("api/v1/conversations/getConversationById")
    Call<GetConversationByIdResponse> getConversationById(@Header("Authorization") String token, @Body GetConversationByIdRequest getConversationRequest);

}
