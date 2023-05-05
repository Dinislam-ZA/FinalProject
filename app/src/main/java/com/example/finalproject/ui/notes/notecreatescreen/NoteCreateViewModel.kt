package com.example.finalproject.ui.notes.notecreatescreen

import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.finalproject.data.model.Category
import com.example.finalproject.data.model.Note
import com.example.finalproject.data.repositories.CategoryRepo
import com.example.finalproject.data.repositories.NoteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class NoteCreateViewModel(private val noteRepo: NoteRepo, private val categoryRepo: CategoryRepo) : ViewModel() {

    var noteLive: MutableLiveData<Note?> = MutableLiveData(null)
    var categoryLive: MutableLiveData<Category?> = MutableLiveData(null)
    val categories = categoryRepo.getAllCategories().flowOn(Dispatchers.IO)

    fun createNote(title:String, noteDes:String, categoryId:Long?){
        viewModelScope.launch(Dispatchers.IO) {
            if (title.isNotBlank()&& title.isNotEmpty()){
                val date = LocalDateTime.now().toLocalDate().toString()
                val note = Note(null ,title, noteDes, date, "Author" ,null,categoryId)
                noteRepo.insertNote(note)
            }
        }
    }

    fun updateNote(title:String, noteDes:String, categoryId:Long?){
        viewModelScope.launch(Dispatchers.IO) {
            if (title.isNotBlank()&& title.isNotEmpty()){
                val date = LocalDateTime.now().toLocalDate().toString()
                val note = Note(noteLive.value?.id,title, noteDes, noteLive.value?.createdAt ?: date, "Author" ,categoryId)
                noteRepo.updateNote(note)
            }
        }
    }

    fun findNoteByTitle(title:String){
        var note:Note? = null
        var category: Category? = null
        viewModelScope.launch(Dispatchers.IO) {
            note = noteRepo.findNoteByTitle(title)
            category = note?.categorie?.let { categoryRepo.findCategoryById(it) }
            noteLive.postValue(note)
            categoryLive.postValue(category)
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
                    NoteRepo(application.baseContext), CategoryRepo(application.baseContext)
                ) as T
            }
        }
    }
}