package com.example.finalproject.data.retrofit

import com.example.finalproject.data.model.LoginRequest
import com.example.finalproject.data.model.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthService {

    @POST("users/registrations")
    suspend fun registerUser(@Body userDetail: User): Response<User>

    @POST("login")
    @Headers("Content-Type: application/json")
    suspend fun loginUser(@Body userDetail: LoginRequest): Response<ResponseBody>
}