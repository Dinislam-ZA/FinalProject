package com.example.finalproject.ui.schedule.schedulemainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.finalproject.data.repositories.CategoryRepo
import com.example.finalproject.data.repositories.TaskRepo
import com.example.finalproject.ui.tasks.taskscreatescreen.TaskCreateViewModel

class ScheduleMainViewModel : ViewModel() {



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


                return ScheduleMainViewModel(

                ) as T
            }
        }
    }
}