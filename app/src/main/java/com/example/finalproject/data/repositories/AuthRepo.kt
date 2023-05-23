package com.example.finalproject.data.repositories

import android.content.Context
import com.example.finalproject.data.model.LoginRequest
import com.example.finalproject.data.model.User
import com.example.finalproject.data.retrofit.RetrofitClient
import com.example.finalproject.data.sharedpreference.SecureStorage
import okhttp3.ResponseBody
import retrofit2.Response

class AuthRepo(context: Context) {

    private val authService = RetrofitClient.authService

    private val secureStorage = SecureStorage(context)

    fun saveToken(value: String){
        secureStorage.token = value
    }

    fun savePassword(value: String){
        secureStorage.password = value
    }

    fun saveUsername(value: String){
        secureStorage.username = value
    }

    fun getToken():String?{
        val token = secureStorage.token
        if (token!= null) return token
        return null
    }

    fun getPassword():String?{
        val password = secureStorage.password
        if (password!= null) return password
        return null
    }

    fun getUsername():String?{
        val username = secureStorage.username
        if (username!= null) return username
        return null
    }

    suspend fun loginUser(userDetails: LoginRequest): Response<ResponseBody> {
        return authService.loginUser(userDetails)
    }

    suspend fun attemptRegistration(username:String, password: String, phoneNumber:String): Response<User> {
        val userDetails = User(username, password, phoneNumber)
        return RetrofitClient.authService.registerUser(userDetails)
    }

}