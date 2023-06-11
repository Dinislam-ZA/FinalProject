package com.example.finalproject.ui.auth.registerscreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.finalproject.data.repositories.AuthRepo
import com.example.finalproject.ui.auth.loginscreen.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(val authRepo: AuthRepo) : ViewModel() {

    val isOk: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    val isBad: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    fun tryRegistration(username:String, password: String, passwordRepeat:String, email:String){
        viewModelScope.launch(Dispatchers.Main) {
            val res = authRepo.attemptRegistration(username, password, email)
            if(res.code() == 200){
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


                return RegisterViewModel(
                    AuthRepo(application.baseContext)
                ) as T
            }
        }
    }
}