package com.example.finalproject.ui.notes.notecreatescreen

import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.finalproject.data.model.Note
import com.example.finalproject.data.repositories.NoteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class NoteCreateViewModel(private val noteRepo: NoteRepo) : ViewModel() {

    var noteLive: MutableLiveData<Note?> = MutableLiveData(null)

    fun createNote(title:String, noteDes:String){
        viewModelScope.launch(Dispatchers.IO) {
            if (title.isNotBlank()&& title.isNotEmpty()){
                val date = LocalDateTime.now().toLocalDate().toString()
                val note = Note(null ,title, noteDes, date)
                noteRepo.insertNote(note)
            }
        }
    }

    fun findNoteByTitle(title:String){
        var note:Note? = null
        viewModelScope.launch(Dispatchers.IO) {
            note = noteRepo.findNoteByTitle(title)
            noteLive.postValue(note)
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
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras


                return NoteCreateViewModel(
                    NoteRepo(application.baseContext)
                ) as T
            }
        }
    }
}