package com.SynClick.quiziniapp.Data.DAOs

import com.SynClick.quiziniapp.Data.DAOs.serverSevices.Services
import com.SynClick.quiziniapp.Data.Data
import com.SynClick.quiziniapp.Data.Models.RequestsModel.GetAllTopicResponse
import com.google.gson.Gson
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class TopicDAO {
    suspend fun getAllTopics(): Pair<Boolean, String> {
        val gson = Gson()
        val call: Call<GetAllTopicResponse> = Services.getTopicService().getAllTopics("Bearer "+ Data.token)

        return suspendCancellableCoroutine { continuation ->
            call.enqueue(object : Callback<GetAllTopicResponse> {
                override fun onResponse(call: Call<GetAllTopicResponse>, response: Response<GetAllTopicResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        // If successful and body is not null, use the message from the body
                        val responseMessage = response.body()?.message ?: "User signed in successfully"
                        continuation.resume(Pair(true, responseMessage))
                        Data.topics=response.body()?.topics
                        println("Response message: $responseMessage")
                    } else {
                        // If not successful, deserialize the error message from errorBody
                        val errorMessage = response.errorBody()?.string()?.let {
                            gson.fromJson(it, GetAllTopicResponse::class.java).message
                        } ?: "Unknown error"
                        continuation.resume(Pair(false, errorMessage))
                        println("Error response message: $errorMessage")
                    }
                }

                override fun onFailure(call: Call<GetAllTopicResponse>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}
