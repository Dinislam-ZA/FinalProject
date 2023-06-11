package com.example.finalproject.ui.habits.habitsmainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.finalproject.data.model.Habit
import com.example.finalproject.data.repositories.CategoryRepo
import com.example.finalproject.data.repositories.HabitRepo
import com.example.finalproject.data.repositories.NoteRepo
import com.example.finalproject.ui.notes.notecreatescreen.NoteCreateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class HabitsMainViewModel(private val repository: HabitRepo, private val categoryRepo: CategoryRepo) : ViewModel() {

    val allHabits: Flow<List<Habit>> = repository.allHabits.flowOn(Dispatchers.IO)
    val categories = categoryRepo.getAllCategories().flowOn(Dispatchers.IO)

//    fun getHabit(habitId: Long): Habit{
//        viewModelScope.launch {
//            repository.getHabit(habitId)
//        }
//    }



    fun insert(habit: Habit) = viewModelScope.launch {
        repository.insert(habit)
    }

    fun update(habit: Habit) = viewModelScope.launch {
        repository.update(habit)
    }

    fun delete(habit: Habit) = viewModelScope.launch {
        repository.delete(habit)
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


                return HabitsMainViewModel(
                    HabitRepo(application.baseContext), CategoryRepo(application.baseContext)
                ) as T
            }
        }
    }
}