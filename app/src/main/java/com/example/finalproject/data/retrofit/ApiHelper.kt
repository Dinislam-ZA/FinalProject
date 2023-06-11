package com.example.finalproject.data.retrofit

import com.example.finalproject.data.model.UserVO
import kotlinx.coroutines.flow.Flow

interface ApiHelper {

    fun getUsers(token: String): Flow<List<UserVO>>

    fun getFriends(token: String): Flow<List<UserVO>>

    fun getFriendshipRequests(token: String): Flow<List<UserVO>>
}