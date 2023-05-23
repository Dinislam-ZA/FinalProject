package com.example.finalproject.ui.auth.loginscreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.finalproject.data.model.LoginRequest
import com.example.finalproject.data.repositories.AuthRepo
import kotlinx.coroutines.launch


class LoginViewModel(val authRepo: AuthRepo) : ViewModel() {

    val isOk: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    val isBad: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    fun loginUser(userDetails: LoginRequest){
        viewModelScope.launch {
            val res = authRepo.loginUser(userDetails)
            if(res.code() == 200){
                val token = res.headers() ["Authorization"] as String
                Log.d("opa", token)
                authRepo.saveToken(token)
                authRepo.saveUsername(userDetails.username)
                authRepo.savePassword(userDetails.password)
                isOk.postValue(true)
            }
            else{
                isBad.postValue(true)
                Log.d("opa", "herovo")
            }
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


                return LoginViewModel(
                    AuthRepo(application.baseContext)
                ) as T
            }
        }
    }
}