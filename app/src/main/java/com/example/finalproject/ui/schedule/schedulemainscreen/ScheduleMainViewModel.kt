package com.example.finalproject.ui.schedule.schedulemainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.finalproject.data.repositories.CategoryRepo
import com.example.finalproject.data.repositories.ScheduleRepo
import com.example.finalproject.data.repositories.TaskRepo
import com.example.finalproject.ui.tasks.taskscreatescreen.TaskCreateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class ScheduleMainViewModel(private val scheduleRepo: ScheduleRepo, private val categoryRepo: CategoryRepo) : ViewModel() {

    val tasks = scheduleRepo.getAllTasks().flowOn(Dispatchers.IO)
    val categories = categoryRepo.getAllCategories().flowOn(Dispatchers.IO)

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
                    ScheduleRepo(application.baseContext), CategoryRepo(application.baseContext)
                ) as T
            }
        }
    }
}