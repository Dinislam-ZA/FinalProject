package com.example.finalproject.ui.notes.notecreatescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.finalproject.data.repositories.NoteRepo

class NoteCreateViewModel(private val noteRepo: NoteRepo) : ViewModel() {





    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras


                return NoteCreateViewModel(
                    NoteRepo(application.baseContext)
                ) as T
            }
        }
    }
}