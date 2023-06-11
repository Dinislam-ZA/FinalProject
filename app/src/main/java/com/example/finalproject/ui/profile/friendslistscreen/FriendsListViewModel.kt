package com.example.finalproject.ui.profile.friendslistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.finalproject.data.repositories.UserRepo
import com.example.finalproject.ui.profile.profilemainscreen.ProfileMainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class FriendsListViewModel(val repo: UserRepo) : ViewModel() {

    var friends = repo.allFriends().flowOn(Dispatchers.IO)
    var requests = repo.allFriendshipRequests().flowOn(Dispatchers.IO)

    fun acceptFriendship(userId:Long){
        viewModelScope.launch {
            repo.acceptFriendship(userId)
        }
    }

    fun rejectFriendship(userId:Long){
        viewModelScope.launch {
            repo.rejectFriendship(userId)
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


                return FriendsListViewModel(
                    UserRepo(application.baseContext)
                ) as T
            }
        }
    }
}