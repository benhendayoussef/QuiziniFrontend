package com.SynClick.quiziniapp.Data.DAOs

import com.SynClick.quiziniapp.Data.DAOs.serverSevices.Services
import com.SynClick.quiziniapp.Data.Data
import com.SynClick.quiziniapp.Data.Models.RequestsModel.RequestResponse
import com.SynClick.quiziniapp.Data.Models.userEntityDto
import com.google.gson.Gson
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class UserDAO {
    suspend fun login(userEntityDto: userEntityDto): Pair<Boolean, String> {
        val gson = Gson()
        val call: Call<RequestResponse> = Services.getClientService().signIn(userEntityDto)

        return suspendCancellableCoroutine { continuation ->
            call.enqueue(object : Callback<RequestResponse> {
                override fun onResponse(call: Call<RequestResponse>, response: Response<RequestResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        // If successful and body is not null, use the message from the body
                        val responseMessage = response.body()?.message ?: "User signed in successfully"
                        Data.token = response.body()?.token ?: ""
                        println("Token: ${Data.token}")
                        continuation.resume(Pair(true, responseMessage))
                        println("Response message: $responseMessage")
                    } else {
                        // If not successful, deserialize the error message from errorBody
                        val errorMessage = response.errorBody()?.string()?.let {
                            gson.fromJson(it, RequestResponse::class.java).message
                        } ?: "Unknown error"
                        continuation.resume(Pair(false, errorMessage))
                        println("Error response message: $errorMessage")
                    }
                }

                override fun onFailure(call: Call<RequestResponse>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}
