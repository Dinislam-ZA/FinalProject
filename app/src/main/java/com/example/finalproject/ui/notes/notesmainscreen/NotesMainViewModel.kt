package com.example.finalproject.ui.notes.notesmainscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.finalproject.data.model.Category
import com.example.finalproject.data.model.Note
import com.example.finalproject.data.repositories.CategoryRepo
import com.example.finalproject.data.repositories.NoteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class NotesMainViewModel(private val noteRepo: NoteRepo, private val categoryRepo: CategoryRepo) : ViewModel() {



    val notes = noteRepo.getAllNotes().flowOn(Dispatchers.IO)
    val categories = categoryRepo.getAllCategories().flowOn(Dispatchers.IO)

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
                    NoteRepo(application.baseContext),  CategoryRepo(application.baseContext)
                ) as T
            }
        }
    }
}