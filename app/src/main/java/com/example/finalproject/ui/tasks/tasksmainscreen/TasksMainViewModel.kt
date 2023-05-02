package com.example.finalproject.ui.tasks.tasksmainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.finalproject.data.model.Task
import com.example.finalproject.data.repositories.CategoryRepo
import com.example.finalproject.data.repositories.TaskRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class TasksMainViewModel (private val taskRepo: TaskRepo,private val categoryRepo: CategoryRepo): ViewModel() {

    val tasks = taskRepo.getAllTasks().flowOn(Dispatchers.IO)
    val categories = categoryRepo.getAllCategories().flowOn(Dispatchers.IO)

    fun deleteTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            taskRepo.deleteTask(task)
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


                return TasksMainViewModel(
                    TaskRepo(application.baseContext),  CategoryRepo(application.baseContext)
                ) as T
            }
        }
    }
}