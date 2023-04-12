package com.example.finalproject.ui.notes.notesmainscreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.finalproject.data.model.Note
import com.example.finalproject.data.repositories.NoteRepo
import com.example.finalproject.ui.notes.notecreatescreen.NoteCreateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class NotesMainViewModel(private val noteRepo: NoteRepo) : ViewModel() {


    val notes = noteRepo.getAllNotes().flowOn(Dispatchers.IO)

    fun deleteNote(note:Note){
        viewModelScope.launch(Dispatchers.IO) {
            noteRepo.deleteNote(note)
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


                return NotesMainViewModel(
                    NoteRepo(application.baseContext)
                ) as T
            }
        }
    }
}