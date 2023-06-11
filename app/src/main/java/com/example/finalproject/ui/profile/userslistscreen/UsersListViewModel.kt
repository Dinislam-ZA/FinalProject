package com.example.finalproject.ui.profile.userslistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.finalproject.data.repositories.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class UsersListViewModel(private val repo:UserRepo) : ViewModel() {

    var users = repo.allUsers().flowOn(Dispatchers.IO)

    fun sendFriendshipRequest(userId:Long){
        viewModelScope.launch {
            repo.sendFriendshipRequest(userId)
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras


                return UsersListViewModel(
                    UserRepo(application.baseContext)
                ) as T
            }
        }
    }
}