package com.example.finalproject.data.retrofit

import com.example.finalproject.data.model.LoginRequest
import com.example.finalproject.data.model.User
import com.example.finalproject.data.model.UserVO
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("users/registrations")
    suspend fun registerUser(@Body userDetail: User): Response<User>

    @POST("login")
    @Headers("Content-Type: application/json")
    suspend fun loginUser(@Body userDetail: LoginRequest): Response<ResponseBody>

    @GET("users")
    suspend fun listUser(@Header("Authorization") auth: String): List<UserVO>

    @GET("friends")
    suspend fun listFriends(@Header("Authorization") auth: String): List<UserVO>

    @POST("friends/{user_id}/add")
    suspend fun addFriend(@Path("user_id") userId:Long, @Header("Authorization") auth: String): Response<ResponseBody>

    @GET("friends")
    suspend fun listRequests(@Header("Authorization") auth: String): List<UserVO>

    @PUT("friends/{user_id}/accept")
    suspend fun acceptFriend(@Path("user_id") userId:Long, @Header("Authorization") auth: String): Response<ResponseBody>

    @PUT("friends/{user_id}/reject")
    suspend fun rejectFriend(@Path("user_id") userId:Long, @Header("Authorization") auth: String): Response<ResponseBody>
}