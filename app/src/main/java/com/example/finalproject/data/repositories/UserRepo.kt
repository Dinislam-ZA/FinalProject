package com.example.finalproject.data.repositories

import android.content.Context
import com.example.finalproject.data.retrofit.ApiHelperImpl
import com.example.finalproject.data.retrofit.RetrofitClient
import com.example.finalproject.data.sharedpreference.SecureStorage

class UserRepo(context: Context) {

    private val userService = RetrofitClient.apiService
    private val apiHelper = ApiHelperImpl(userService)
    private val secureStorage = SecureStorage(context)
    private val token = secureStorage.token!!

    fun allUsers() = apiHelper.getUsers(token)

    fun allFriends() = apiHelper.getFriends(token)

    fun allFriendshipRequests() = apiHelper.getFriendshipRequests(token)

    suspend fun acceptFriendship(userId: Long){
        userService.acceptFriend(userId, token)
    }

    suspend fun rejectFriendship(userId: Long){
        userService.rejectFriend(userId, token)
    }

    suspend fun sendFriendshipRequest(userId: Long){
        userService.addFriend(userId, token)
    }
}