package com.example.finalproject.data.retrofit

import com.example.finalproject.data.model.UserVO
import com.example.finalproject.data.sharedpreference.SecureStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiHelperImpl(private val apiService: ApiService): ApiHelper {


    override fun getUsers(token: String): Flow<List<UserVO>> =
        flow{
            emit(apiService.listUser(token))
        }

    override fun getFriends(token: String): Flow<List<UserVO>> =
        flow{
            emit(apiService.listFriends(token))
        }


    override fun getFriendshipRequests(token: String): Flow<List<UserVO>> =
        flow{
            emit(apiService.listRequests(token))
        }

}